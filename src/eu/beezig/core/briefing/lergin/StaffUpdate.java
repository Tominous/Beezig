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

package eu.beezig.core.briefing.lergin;

public class StaffUpdate {

    private StaffChangeType type;
    private String staffName;

    public StaffUpdate(StaffChangeType type, String staffName) {
        this.type = type;
        this.staffName = staffName;
    }

    public StaffChangeType getType() {
        return type;
    }

    public String getStaffName() {
        return staffName;
    }


}
