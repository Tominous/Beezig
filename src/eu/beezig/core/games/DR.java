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
import eu.beezig.core.hiveapi.stuff.dr.DRMap;
import eu.beezig.core.hiveapi.stuff.dr.DRRank;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.server.GameMode;
import eu.the5zig.mod.server.GameState;
import pw.roccodev.beezig.hiveapi.wrapper.monthly.dr.DrMonthlyProfile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DR extends GameMode {

    public static DRMap activeMap;
    public static String currentMapPB;
    public static String currentMapWR;
    public static String currentMapWRHolder;
    public static boolean dead = false;
    public static String role = null;
    public static String mapTime;
    public static int checkpoints;
    public static int deaths;
    public static int kills;
    public static int lastPts;
    public static String gameId = null;
    public static HashMap<String, DRMap> mapsPool;
    public static int dailyPoints;
    public static String rank;
    public static DRRank rankObject;
    public static List<String> votesToParse = new ArrayList<>();
    public static boolean hasVoted = false;
    public static List<String> messagesToSend = new ArrayList<>();
    public static List<String> footerToSend = new ArrayList<>();
    private static GameLogger logger;
    private static PrintWriter dailyPointsWriter;
    private static String dailyPointsName;

    public static DrMonthlyProfile monthly;
    public static boolean attemptNew = true;
    public static boolean hasLoaded = false;

    public static void initDailyPointsWriter() throws IOException {

        logger = new GameLogger(BeezigMain.mcFile + "/dr/games.csv");
        logger.setHeaders(new String[]{
                "Role",
                "Map",
                "Kills",
                "Deaths",
                "GameID",
                "Timestamp",
                "Time"
        });

        File f = new File(BeezigMain.mcFile + "/dr/dailyPoints/" + dailyPointsName);
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
            DR.dailyPoints = Integer.parseInt(line);
        }
        stream.close();
        reader.close();

    }

    private static void initPointsWriterWithZero() throws FileNotFoundException, UnsupportedEncodingException {
        dailyPointsWriter = new PrintWriter(BeezigMain.mcFile + "/dr/dailyPoints/" + dailyPointsName, "UTF-8");
        dailyPointsWriter.println(0);

        dailyPointsWriter.close();


    }

    public static void setDailyPointsFileName(String newName) {
        dailyPointsName = newName;
    }

    private static void saveDailyPoints() {
        try {
            dailyPointsWriter = new PrintWriter(BeezigMain.mcFile + "/dr/dailyPoints/" + dailyPointsName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dailyPointsWriter.println(dailyPoints);
        dailyPointsWriter.flush();
        dailyPointsWriter.close();
    }

    public static void reset(DR gm) {

        gm.setState(GameState.FINISHED);
        if (role != null && activeMap != null && logger != null)
            logger.logGame(role, activeMap.getDisplayName(), kills + "", deaths + "", gameId, System.currentTimeMillis() + "", mapTime);
        activeMap = null;
        currentMapPB = null;
        currentMapWR = null;
        gameId = null;
        currentMapWRHolder = null;
        role = null;
        checkpoints = 0;
        deaths = 0;
        kills = 0;
        lastPts = 0;
        hasLoaded = false;
        mapTime = null;
        DR.hasVoted = false;
        ActiveGame.reset("dr");
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
        return "Deathrun";
    }

}
