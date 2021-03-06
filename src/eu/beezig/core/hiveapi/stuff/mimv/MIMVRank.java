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

package eu.beezig.core.hiveapi.stuff.mimv;

import eu.beezig.core.Log;
import eu.beezig.core.hiveapi.stuff.RankEnum;
import eu.the5zig.mod.The5zigAPI;

import java.util.ArrayList;
import java.util.Arrays;

public enum MIMVRank implements RankEnum {

    PACIFIST("Pacifist", "§7", 0),
    CITIZEN("Citizen", "§6", 150),
    OBSERVER("Observer", "§d", 1000),
    BYSTANDER("Bystander", "§b", 2250),
    BUTCHER("Butcher", "§e", 3500),
    BANDIT("Bandit", "§a", 5500),
    REBEL("Rebel", "§c", 8000),
    EXECUTIONER("Executioner", "§e", 11500),
    PSYCHO("Psycho", "§6", 18000),
    ASSASSIN("Assassin", "§5", 25000),
    HUNTSMAN("Huntsman", "§6§l", 35000),
    BOUNTY_HUNTER("Bounty Hunter", "§b§l", 55000),
    SHADOW("Shadow", "§c§l", 75000),
    ENFORCER("Enforcer", "§a§l", 100000),
    ALTAIR("Altair", "§e§l", 125000),
    EZIO("Ezio", "§d§l", 175000),
    MUCKDUCK("❖ Muckduck", "§5§l", -1);

    private String display, prefix;
    private int start;

    MIMVRank(String display, String prefix, int start) {
        this.display = display;
        this.prefix = prefix;
        this.start = start;
    }

    public static MIMVRank getFromDisplay(String display) {
        for (MIMVRank rank : MIMVRank.values()) {
            if (rank.getDisplay().equalsIgnoreCase(display)) return rank;
        }
        return null;
    }

    public String getDisplay() {
        return display;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getStart() {
        return start;
    }

    public String getTotalDisplay() {
        return prefix + display;
    }

    public String getPointsToNextRank(int points) {
        if (this == MUCKDUCK) return "Leaderboard Rank";
        if (this == EZIO) return "Highest Rank";
        ArrayList<MIMVRank> ranks = new ArrayList<>(Arrays.asList(values()));
        int newIndex = ranks.indexOf(this) + 1;
        MIMVRank next;
        try {
            next = ranks.get(newIndex);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


        return The5zigAPI.getAPI().translate("beezig.str.tonextrank", next.prefix + Log.df(next.getStart() - points), next.getTotalDisplay());
    }

}
