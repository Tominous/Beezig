package tk.roccodev.zta;

import eu.the5zig.mod.The5zigAPI;

public class Log {

	
	public static String info = "§7▏ §aBeezig§7 ▏ §e";
	public static String error = "§7▏ §cBeezig§7 ▏ §c";
	
	public static String getUserAgent() {
		return "Beezig/" + ZTAMain.BEEZIG_VERSION + " (5zig/" + The5zigAPI.getAPI().getModVersion() + " on " + The5zigAPI.getAPI().getMinecraftVersion() + "; Forge=" + The5zigAPI.getAPI().isForgeEnvironment() + ")";
	}

}
