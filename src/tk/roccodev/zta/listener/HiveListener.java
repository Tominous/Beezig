package tk.roccodev.zta.listener;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.server.AbstractGameListener;
import eu.the5zig.mod.server.GameMode;
import eu.the5zig.mod.server.GameState;
import eu.the5zig.mod.server.IPatternResult;
import eu.the5zig.util.minecraft.ChatColor;
import tk.roccodev.zta.hiveapi.HiveAPI;

public class HiveListener extends AbstractGameListener<GameMode>{

	@Override
	public Class<GameMode> getGameMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean matchLobby(String lobby) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	@Override
	public boolean onServerChat(GameMode gameMode, String message) {
		if(message.contains("§eGold Medal Awarded!")){
			HiveAPI.medals++;
		}
		else if(message != null && ChatColor.stripColor(message).contains("▍ Tokens ▏ You earned")){
			
			String[] data = ChatColor.stripColor(message).replaceAll("▍ Tokens ▏ You earned", "").split("tokens");
			
			int tokens = Integer.parseInt(data[0].trim());
			
			HiveAPI.tokens += tokens;
			
		}
		return false;
	}

	@Override
    public void onMatch(GameMode gameMode, String key, IPatternResult match) {
		 if (gameMode != null && gameMode.getState() != GameState.FINISHED) {
	            return;
	        }
		if(key.equals("timv.welcome")){
			getGameListener().switchLobby("TIMV");
			
			The5zigAPI.getLogger().info("Connected to TIMV! -Hive");
		}
		
		else if(key.equals("dr.welcome")){
			getGameListener().switchLobby("DR");
			
			The5zigAPI.getLogger().info("Connected to DR! -Hive");
		}
		else if(key.equals("bed.welcome") || (key.equals("bed.spectator") && gameMode == null)){
			getGameListener().switchLobby("BED");
			
			The5zigAPI.getLogger().info("Connected to BED/BEDT! -Hive");
		}
		
	}

	

}
