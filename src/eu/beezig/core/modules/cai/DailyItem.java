package eu.beezig.core.modules.cai;

import eu.beezig.core.Log;
import eu.the5zig.mod.modules.GameModeItem;
import eu.beezig.core.games.CAI;

public class DailyItem extends GameModeItem<CAI> {

    public DailyItem() {
        super(CAI.class);
    }

    @Override
    protected Object getValue(boolean dummy) {

        return Log.df(CAI.dailyPoints) + " " + Log.t("beezig.module.points");

    }

    @Override
    public String getTranslation() { return "beezig.module.daily";}

    @Override
    public boolean shouldRender(boolean dummy) {
        try {
            if (getGameMode() == null)
                return false;
            return dummy || (CAI.shouldRender(getGameMode().getState()));
        } catch (Exception e) {
            return false;
        }
    }

}