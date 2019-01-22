package eu.beezig.core.modules.timv;

import eu.the5zig.mod.modules.GameModeItem;
import eu.the5zig.mod.server.GameState;
import eu.beezig.core.Log;
import eu.beezig.core.games.TIMV;

public class BodiesItem extends GameModeItem<TIMV> {

    public BodiesItem() {
        super(TIMV.class);
    }

    @Override
    protected Object getValue(boolean dummy) {

        return TIMV.traitorsDiscovered + "/" + TIMV.traitorsBefore + " " + Log.t("beezig.str.timv.traitors");

    }

    @Override
    public String getTranslation() { return "beezig.module.timv.bodies";}

    // LabyMod
    protected String getCategoryKey() {
        return "timv";
    }

    @Override
    public boolean shouldRender(boolean dummy) {
        try {
            if (getGameMode() == null) return false;
            return dummy || (getGameMode().getState() == GameState.GAME);
        } catch (Exception e) {
            return false;
        }
    }

}