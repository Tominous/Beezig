package tk.roccodev.zta.hiveapi.wrapper.modes;

import org.json.simple.JSONObject;

import eu.the5zig.mod.server.GameMode;
import eu.the5zig.util.minecraft.ChatColor;
import tk.roccodev.zta.hiveapi.wrapper.APIGameMode;
import tk.roccodev.zta.hiveapi.wrapper.NetworkRank;

public class ApiHiveGlobal extends APIGameMode {

	public ApiHiveGlobal(String playerName) {
		super(playerName);
		
	}

	
	
	@Override
	public Class<? extends GameMode> getGameMode() {
		// TODO Auto-generated method stub
		return GameMode.class;
	}

	@Override
	public String getShortcode() {
		// TODO Auto-generated method stub
		return "";
	}
	
	
	
	public String getNetworkTitle(){
		return (String) object("rankName");
	}
	
	public ChatColor getNetworkRankColor(){
		return NetworkRank.fromDisplay(getNetworkTitle()).getColor();
	}
	
	public String getCorrectName(){
		return (String) object("username");
	}
	
	public long getLastLogout(){
		return (long) object("lastLogout");
	}
	
	public String getPlayerLocation(){
		//?
		JSONObject o = (JSONObject) object("status");
		
		if(o.get("game").toString().equals("Hubs"))	{
			return "Hub";
		}
		return (String) o.get("game");
	}
	
	
	
	

}