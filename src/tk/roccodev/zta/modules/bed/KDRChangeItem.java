package tk.roccodev.zta.modules.bed;

import java.text.DecimalFormat;

import eu.the5zig.mod.modules.GameModeItem;
import tk.roccodev.zta.games.BED;

public class KDRChangeItem extends GameModeItem<BED>{

	public KDRChangeItem(){
		super(BED.class);
	}

	@Override
	protected Object getValue(boolean dummy) {
		try{		
			
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			
			return df.format(BED.gameKdr) + " (" + (BED.gameKdr - BED.apiKdr > 0 ? "+" + df.format(BED.gameKdr - BED.apiKdr) : df.format(BED.gameKdr - BED.apiKdr)) + ")";
		}catch(Exception e){
				e.printStackTrace();
				return "Server error";
		}
	}
	
	@Override
	public String getName() {
		return "KDR Change";
	}
	
	@Override
	public boolean shouldRender(boolean dummy){		
		try{
			if(!(getGameMode() instanceof BED)) return false;
		return dummy || (BED.shouldRender(getGameMode().getState()) && (BED.apiKdr - BED.gameKdr != 0));
		}catch(Exception e){
			return false;
		}
	}

}