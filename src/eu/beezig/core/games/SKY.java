/*
 * Copyright (C) 2019 Beezig (RoccoDev, ItsNiklass)
 *
 * This file is part of Beezig.
 *
 * Beezig is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beezig is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Beezig.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.core.games;

import eu.beezig.core.ActiveGame;
import eu.beezig.core.BeezigMain;
import eu.beezig.core.IHive;
import eu.beezig.core.games.logging.GameLogger;
import eu.beezig.core.hiveapi.stuff.sky.SKYRank;
import eu.beezig.core.utils.StreakUtils;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.server.GameMode;
import eu.the5zig.mod.server.GameState;
import pw.roccodev.beezig.hiveapi.wrapper.monthly.sky.SkyMonthlyProfile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SKY extends GameMode {

    public static List<String> messagesToSend = new ArrayList<>();
    public static List<String> footerToSend = new ArrayList<>();
    public static boolean hasVoted = false;
    public static List<String> votesToParse = new ArrayList<>();
    public static String mode;
    public static long gamePoints;
    public static int totalKills;
    public static int kills;
    public static int deaths;
    public static boolean inGame;
    public static boolean hasWon;
    public static int winstreak;
    public static int bestStreak;
    public static int apiKills, apiDeaths;
    public static double apiKdr;
    public static double gameKdr;
    public static int dailyPoints;
    public static String rank;
    public static String team;
    public static SKYRank rankObject;
    public static String map;
    public static String gameId;
    private static GameLogger logger;
    private static PrintWriter dailyPointsWriter;
    private static String dailyPointsName;

    public static SkyMonthlyProfile monthly;
    public static boolean attemptNew = true;
    public static boolean hasLoaded = false;

    public static void updateKdr() {
        apiKdr = (double) apiKills / (apiDeaths == 0 ? 1 : apiDeaths);
        gameKdr = (double) (kills + apiKills) / (deaths + apiDeaths == 0 ? 1 : apiDeaths + deaths);
    }

    public static void initDailyPointsWriter() throws IOException {

        logger = new GameLogger(BeezigMain.mcFile + "/sky/games.csv");
        logger.setHeaders(new String[]{
                "Points",
                "Map",
                "Kills",
                "Mode",
                "Victory?",
                "Timestamp",
                "GameID"
        });

        File f = new File(BeezigMain.mcFile + "/sky/dailyPoints/" + dailyPointsName);
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
            SKY.dailyPoints = Integer.parseInt(line);
        }
        stream.close();
        reader.close();

    }

    private static void initPointsWriterWithZero() throws FileNotFoundException, UnsupportedEncodingException {
        dailyPointsWriter = new PrintWriter(BeezigMain.mcFile + "/sky/dailyPoints/" + dailyPointsName, "UTF-8");
        dailyPointsWriter.println(0);

        dailyPointsWriter.close();


    }

    public static void setDailyPointsFileName(String newName) {
        dailyPointsName = newName;
    }

    private static void saveDailyPoints() {
        try {
            dailyPointsWriter = new PrintWriter(BeezigMain.mcFile + "/sky/dailyPoints/" + dailyPointsName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dailyPointsWriter.println(dailyPoints);
        dailyPointsWriter.flush();
        dailyPointsWriter.close();
    }

    public static void reset(SKY gameMode) {

        gameMode.setState(GameState.FINISHED);
        if (inGame && !hasWon) {
            boolean wasBest = winstreak >= bestStreak;
            System.out.println("Lost!");
            winstreak = 0;
            StreakUtils.resetWinstreak("sky", wasBest);
        }

        if (inGame && logger != null)
            logger.logGame(gamePoints + "", map, kills + "", mode, hasWon ? "Yes" : "No", System.currentTimeMillis() + "", gameId);

        gameId = null;
        hasWon = false;
        inGame = false;
        SKY.messagesToSend.clear();
        SKY.footerToSend.clear();
        SKY.votesToParse.clear();

        SKY.hasVoted = false;
        gamePoints = 0;
        team = "";
        hasLoaded = false;
        mode = "";
        kills = 0;
        deaths = 0;
        ActiveGame.reset("sky");
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

    @Override
    public String getName() {
        return "SkyWars";
    }

}
