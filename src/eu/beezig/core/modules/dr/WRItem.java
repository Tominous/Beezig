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

package eu.beezig.core.modules.dr;

import eu.beezig.core.games.DR;
import eu.the5zig.mod.modules.GameModeItem;

public class WRItem extends GameModeItem<DR> {

    public WRItem() {
        super(DR.class);
    }

    @Override
    protected Object getValue(boolean dummy) {
        if (DR.activeMap != null) {
            if ((boolean) getProperties().getSetting("showusername").get()) {
                return DR.currentMapWR + " (" + DR.currentMapWRHolder + ")";
            }
            return DR.currentMapWR;
        } else {
            return "No Record";
        }

    }

    @Override
    public String getTranslation() {
        return "beezig.module.dr.wr";
    }

    @Override
    public void registerSettings() {
        getProperties().addSetting("showusername", true);
    }

    @Override
    public boolean shouldRender(boolean dummy) {
        try {
            if (getGameMode() == null) return false;
            return dummy || (DR.shouldRender(getGameMode().getState()) && DR.activeMap != null && DR.role.equals("Runner"));
        } catch (Exception e) {
            return false;
        }
    }

}
