package tk.roccodev.zta.modules.sky;

import eu.the5zig.mod.modules.GameModeItem;
import eu.the5zig.util.minecraft.ChatColor;
import tk.roccodev.zta.games.SKY;

public class ModeItem extends GameModeItem<SKY> {

	public ModeItem() {
		super(SKY.class);
	}

	@Override
	protected Object getValue(boolean dummy) {
		try {
			
			return SKY.mode;

		} catch (Exception e) {
			e.printStackTrace();
			return "Server error";
		}
	}

	@Override
	public String getName() {
		return "Mode";
	}

	
	

	@Override
	public boolean shouldRender(boolean dummy) {
		try {
			if (!(getGameMode() instanceof SKY))
				return false;
			if (SKY.mode == null || SKY.mode.isEmpty())
				return false;
			return dummy || (SKY.shouldRender(getGameMode().getState()));
		} catch (Exception e) {
			return false;
		}
	}

}