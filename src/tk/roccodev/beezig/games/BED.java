package tk.roccodev.beezig.games;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.gui.ingame.Scoreboard;
import eu.the5zig.mod.server.GameMode;
import eu.the5zig.mod.server.GameState;
import eu.the5zig.util.minecraft.ChatColor;
import tk.roccodev.beezig.ActiveGame;
import tk.roccodev.beezig.BeezigMain;
import tk.roccodev.beezig.IHive;
import tk.roccodev.beezig.hiveapi.APIValues;
import tk.roccodev.beezig.hiveapi.stuff.bed.BEDRank;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BED extends GameMode {

    public static char[] NUMBERS = {' ', '➊', '➋', '➌', '➍', '➎'};

    public static String activeMap;
    public static String team;
    public static String lastRecords = "";
    public static Long lastRecordsPoints = null;
    public static String mode = "";

    public static int kills;
    public static int deaths;
    public static int pointsCounter;
    public static int bedsDestroyed;
    public static int teamsLeft;

    public static int apiKills;
    public static int apiDeaths;

    // Generators (0: None, 1: Level 1, 2: Level 2, 3: Level 3)

    public static int ironGen;
    public static int goldGen;
    public static int diamondGen;
    public static int dailyPoints;
    public static double apiKdr;
    public static double gameKdr;
    public static String rank;
    public static BEDRank rankObject;
    public static List<String> votesToParse = new ArrayList<>();
    public static boolean hasVoted = false;
    public static Boolean hasWon = null;
    public static List<String> messagesToSend = new ArrayList<>();
    public static List<String> footerToSend = new ArrayList<>();
    public static boolean isRecordsRunning = false;
    public static int winstreak;
    private static PrintWriter dailyPointsWriter;
    private static String dailyPointsName;

    public static void initDailyPointsWriter() throws IOException {
        File f = new File(BeezigMain.mcFile + "/bedwars/dailyPoints/" + dailyPointsName);
        if (!f.exists()) {
            f.createNewFile();
            initPointsWriterWithZero();
            return;
        }
        FileInputStream stream = new FileInputStream(f);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line = reader.readLine();
        if (line == null) {
            initPointsWriterWithZero();
            stream.close();
            return;
        } else {
            BED.dailyPoints = Integer.parseInt(line);
        }
        stream.close();
        reader.close();


    }

    private static void initPointsWriterWithZero() throws FileNotFoundException, UnsupportedEncodingException {
        dailyPointsWriter = new PrintWriter(BeezigMain.mcFile + "/bedwars/dailyPoints/" + dailyPointsName, "UTF-8");
        dailyPointsWriter.println(0);

        dailyPointsWriter.close();


    }

    public static void setDailyPointsFileName(String newName) {
        dailyPointsName = newName;
    }

    private static void saveDailyPoints() {
        try {
            dailyPointsWriter = new PrintWriter(BeezigMain.mcFile + "/bedwars/dailyPoints/" + dailyPointsName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dailyPointsWriter.println(dailyPoints);
        dailyPointsWriter.flush();
        dailyPointsWriter.close();
    }

    // (§8▍ §3§lBed§b§lWars§8 ▏ §e§lVote for a map:)

    public static void reset(BED gm) {

        gm.setState(GameState.FINISHED);
        if (hasWon != null) {
            if (!hasWon) {
                winstreak = 0;
            }
        }
        BED.team = null;
        BED.mode = "";
        BED.activeMap = null;
        BED.hasVoted = false;
        BED.kills = 0;
        BED.deaths = 0;
        BED.bedsDestroyed = 0;
        BED.pointsCounter = 0;
        BED.teamsLeft = 0;
        BED.votesToParse.clear();
        ironGen = 0;
        goldGen = 0;
        diamondGen = 0;
        ActiveGame.reset("bed");
        hasWon = null;
        IHive.genericReset();
        if (The5zigAPI.getAPI().getActiveServer() != null)
            The5zigAPI.getAPI().getActiveServer().getGameListener().switchLobby("");
        saveDailyPoints();
    }

    public static boolean shouldRender(GameState state) {

        if (state == GameState.GAME)
            return true;
        if (state == GameState.PREGAME)
            return true;
        return state == GameState.STARTING;
    }

    public static void updateTeamsLeft() {
        Scoreboard sb = The5zigAPI.getAPI().getSideScoreboard();

        for (Map.Entry<String, Integer> entry : sb.getLines().entrySet()) {

            if (entry.getValue() == 13) {
                BED.teamsLeft = ChatColor.stripColor(entry.getKey()).toCharArray().length;
            }
        }
    }

    public static String updateResources() {
        StringBuilder sb = new StringBuilder();
        int ironIngots = The5zigAPI.getAPI().getItemCount("iron_ingot");
        int goldIngots = The5zigAPI.getAPI().getItemCount("gold_ingot");
        int diamonds = The5zigAPI.getAPI().getItemCount("diamond");
        int emeralds = The5zigAPI.getAPI().getItemCount("emerald");
        if (ironIngots != 0)
            sb.append(ironIngots).append(" Iron / ");
        if (goldIngots != 0)
            sb.append(goldIngots).append(" Gold / ");
        if (diamonds != 0)
            sb.append(diamonds).append(" Diamonds / ");
        if (emeralds != 0)
            sb.append(emeralds).append(" Emeralds / ");

        return sb.toString().trim();
    }

    public static void updateRank() {
        BED.rank = BEDRank.getRank(APIValues.BEDpoints).getName()
                .replaceAll(ChatColor.stripColor(BEDRank.getRank(APIValues.BEDpoints).getName()), "")
                + BED.NUMBERS[BEDRank.getRank(APIValues.BEDpoints).getLevel((int) APIValues.BEDpoints)] + " "
                + BEDRank.getRank((int) APIValues.BEDpoints).getName();
        BED.rankObject = BEDRank.getRank(APIValues.BEDpoints);
    }

    public static void updateKdr() {
        apiKdr = (double) apiKills / (apiDeaths == 0 ? 1 : apiDeaths);
        gameKdr = (double) (kills + apiKills) / (deaths + apiDeaths == 0 ? 1 : apiDeaths + deaths);
    }

    public static void updateMode() {
        // ffs mode is so annoying
        Scoreboard sb = The5zigAPI.getAPI().getSideScoreboard();
        The5zigAPI.getLogger().info(sb.getTitle());
        if (sb != null && sb.getTitle().contains("BED ")) {
            BED.mode = "Solo";
        }
        if (sb != null && sb.getTitle().contains("BEDD ")) {
            BED.mode = "Duo";
        }
        if (sb != null && sb.getTitle().contains("BEDT ")) {
            BED.mode = "Teams";
        }
    }

    @Override
    public String getName() {
        return "Bedwars";
    }

}