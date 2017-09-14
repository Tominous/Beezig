package tk.roccodev.zta.hiveapi.wrapper.modes;

import eu.the5zig.mod.server.GameMode;
import tk.roccodev.zta.games.HIDE;
import tk.roccodev.zta.hiveapi.wrapper.APIGameMode;

import java.util.Date;

public class ApiGiant extends APIGameMode{

	public ApiGiant(String playerName) {
		super(playerName);
	}

	@Override
	public Class<? extends GameMode> getGameMode() {
		// TODO Auto-generated method stub
		return HIDE.class;
	}

	@Override
	public String getShortcode() {
		// TODO Auto-generated method stub
		return "HIDE";
	}

	@Override
	public Date lastPlayed(){
		return new Date((long) object("lastlogin"));
	}

}