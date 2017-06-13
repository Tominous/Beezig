package tk.roccodev.zta.hiveapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.util.NetworkPlayerInfo;
import eu.the5zig.util.minecraft.ChatColor;

public class HiveAPI {
	
	public static long TIMVkarma = 0;
	public static long DRpoints = 0;
	
	private static URL TIMVparsePlayerURL(String name){
		String urls = "http://api.hivemc.com/v1/player/@player@/TIMV";
		try {
			return new URL(urls.replaceAll("@player@", name));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private static URL DRparsePlayerURL(String name){
		String urls = "http://api.hivemc.com/v1/player/@player@/DR";
		try {
			return new URL(urls.replaceAll("@player@", name));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private static URL GameParsePlayerURL(String name, String game){
		String urls = "http://api.hivemc.com/v1/player/@player@/" + game;
		try {
			return new URL(urls.replaceAll("@player@", name));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private static URL parsePlayerURLGeneric(String name){
		String urls = "http://api.hivemc.com/v1/player/@player@/";
		try {
			return new URL(urls.replaceAll("@player@", name));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private static URL parsePlayerURLUUID(String uuid){
		String urls = "http://api.hivemc.com/v1/player/@player@/";
		try {
			return new URL(urls.replaceAll("@player@", uuid));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private static URL parseMojangPlayerAPI(String ign){
		String url = "https://api.mojang.com/users/profiles/minecraft/@ign@";
		try {
			return new URL(url.replaceAll("@ign@", ign));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
 	private static URL parseSpeedruncom(String mapid){
		String urls = "http://www.speedrun.com/api/v1/leaderboards/369ep8dl/level/@id@/824xzvmd?top=1";
		try {
			return new URL(urls.replaceAll("@id@", mapid));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private static URL parseSpeedruncomUserID(String userid){
		String urls = "http://www.speedrun.com/api/v1/users/@id@";
		try {
			return new URL(urls.replaceAll("@id@", userid));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private static URL parseMonthlyURL(String game){
		//APIKey is only used for analytics => doesn't have to be kept secret; be aware that the endpoint URL might change in the future, there will be a notification prior to that
		String url = "https://thtmx.rocks/@game@/api/ighGH789fdf5kfHUo";
		try {
			return new URL(url.replaceAll("@game@", game));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	private static URL parseLeaderboardPlaceURL(int index, String game){
		String url = "http://api.hivemc.com/v1/game/@game@/leaderboard/" + index + "/" + (index + 1);
		try {
			return new URL(url.replaceAll("@game@", game));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//TIMV
	public static void TIMVupdateKarma() throws ParseException, Exception{
		String playername = The5zigAPI.getAPI().getGameProfile().getName();
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			o = (JSONObject) parser.parse(readUrl(TIMVparsePlayerURL(playername)));
		
		TIMVkarma =  (long) o.get("total_points");
		
		
	}
	public static long TIMVgetKarmaPerGame(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(TIMVparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return (long) o.get("most_points");
	}
	public static String TIMVgetRank(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(TIMVparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return (String) o.get("title");
	}
	public static int TIMVgetAchievements(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(TIMVparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		HashMap<String, Object> map = new HashMap<String, Object>();
			Iterator<?> keys = o.keySet().iterator();

	        while( keys.hasNext() ){
	            String key = (String)keys.next();
	            Object value = o.get(key);
	            map.put(key, value);

	        }
	       
	       JSONObject o2 = (JSONObject) map.get("achievements");
	       
	       return o2.keySet().size() - 1;
	}
	public static long TIMVgetRolepoints(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(TIMVparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return (long) o.get("role_points");
	}
	public static long TIMVgetKarma(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(TIMVparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return (long) o.get("total_points");
	}
	
	//DR
	public static long DRgetPoints(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(DRparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return (long) o.get("total_points");
	}
	public static String DRgetRank(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(DRparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return (String) o.get("title");
	}
	public static long DRgetGames(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(DRparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		return (long) o.get("games_played");
	}
	public static long DRgetRunnerWins(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(DRparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		return (long) o.get("runnerwins");
	}
	public static long DRgetRunnerGamesPlayed(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(DRparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		return (long) o.get("runnergamesplayed");
	}
	public static long DRgetDeaths(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(DRparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		return (long) o.get("deaths");
	}
	public static String DRgetPB(String ign, DRMap map){
		// totally the best way to do this 10/10
		String playername = ign;
		String map1 = map.toString();
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		JSONObject o1 = null;
			try {
				o = (JSONObject) parser.parse(readUrl(DRparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				return "No Personal Best";
			}			
		try {
			o1 = (JSONObject) parser.parse(o.get("maprecords").toString());
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "No Personal Best";
		}
		int time = Integer.valueOf(o1.get(map1).toString());
		if(time > 59){
			int seconds = time % 60;
			int minutes = Math.floorDiv(time, 60);
				if(seconds < 10){
					return (minutes + ":0" + seconds);
				}
			return (minutes + ":" + seconds);
		}
		return "0:" + time;
	}
	public static String DRgetWR(DRMap map){
		String mapid = map.getSpeedrunID();
		JSONParser parser = new JSONParser();
		JSONObject run0 = null;
			// run0 = Information about the WR run entry on speedrun.com
			try {
				run0 = (JSONObject) parser.parse(((JSONObject) parser.parse(((JSONArray) parser.parse(((JSONObject) parser.parse(((JSONObject) parser.parse(readUrl(parseSpeedruncom(mapid)))).get("data").toString())).get("runs").toString())).get(0).toString())).get("run").toString());
			} catch (Exception e) {
				
				e.printStackTrace();
				return "No World Record";
			}
		Double time = null;
		try {
			//Returns the world record time in seconds
			time = (Double) ((JSONObject) parser.parse(run0.get("times").toString())).get("primary_t");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "No World Record";
		}
		if(time > 59){
			int seconds = (int) (Math.floor(time) % 60);
			double millis = Math.floor(((time - seconds) - 60)*1000)/1000;
			int minutes = Math.floorDiv((int)(time - millis), 60);
				if(seconds < 10){
					return (minutes + ":0" + (seconds+millis));
				}
			return (minutes + ":" + (seconds+millis));
		}
		return "0:" + time;
	}
	public static String DRgetWRHolder(DRMap map){
		String mapid = map.getSpeedrunID();
		String WRHolder = null;
		JSONParser parser = new JSONParser();
		JSONObject run0 = null;
			// run0 = Information about the WR run entry on speedrun.com
			try {
				run0 = (JSONObject) parser.parse(((JSONObject) parser.parse(((JSONArray) parser.parse(((JSONObject) parser.parse(((JSONObject) parser.parse(readUrl(parseSpeedruncom(mapid)))).get("data").toString())).get("runs").toString())).get(0).toString())).get("run").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "No Holder";
			}
			try {
				//Returns the world record holder username... lmao
				WRHolder = (String) ((JSONObject) parser.parse(((JSONObject) parser.parse(((JSONObject) parser.parse(((JSONObject) parser.parse(readUrl(parseSpeedruncomUserID((String) ((JSONObject) parser.parse(((JSONArray) parser.parse(((JSONObject) parser.parse(run0.toJSONString())).get("players").toString())).get(0).toString())).get("id".toString()))))).toJSONString())).get("data").toString())).get("names").toString())).get("international").toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "No Holder";
			}
			if (WRHolder == null){
				try {
					WRHolder = (String) (((JSONObject) parser.parse(((JSONArray) parser.parse(((JSONObject) parser.parse(run0.toJSONString())).get("players").toString())).get(0).toString())).get("name".toString())).toString();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "No Holder";
				}
			}
		return WRHolder;
	}
	
	public static double DRgetWR_raw(DRMap map){
		String mapid = map.getSpeedrunID();
		JSONParser parser = new JSONParser();
		JSONObject run0 = null;
			// run0 = Information about the WR run entry on speedrun.com
			try {
				run0 = (JSONObject) parser.parse(((JSONObject) parser.parse(((JSONArray) parser.parse(((JSONObject) parser.parse(((JSONObject) parser.parse(readUrl(parseSpeedruncom(mapid)))).get("data").toString())).get("runs").toString())).get(0).toString())).get("run").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Double time = null;
		try {
			//Returns the world record time in seconds
			time = (Double) ((JSONObject) parser.parse(run0.get("times").toString())).get("primary_t");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	
	}
	
	public static int DRgetAchievements(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(DRparsePlayerURL(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		HashMap<String, Object> map = new HashMap<String, Object>();
			Iterator<?> keys = o.keySet().iterator();

	        while( keys.hasNext() ){
	            String key = (String)keys.next();
	            Object value = o.get(key);
	            map.put(key, value);

	        }
	       
	       JSONObject o2 = (JSONObject) map.get("achievements");
	       
	       return o2.keySet().size() - 1;
	}

	public static Date getLastLogout(String ign){
		/*
		 * Fetches the last logout from the API
		 * 
		 * @return last logout
		 * 
		 */
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(parsePlayerURLGeneric(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		long time = (long) o.get("lastLogout");
		return new Date(time * 1000);
	}
	public static String getNetworkRank(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(parsePlayerURLGeneric(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				boolean playerOnline = byName(ign) != null; //If the player is online, we're sure that the player is in Hive's database
				boolean connError = false;
				try{
					//RoccoDev's UUID
					parser.parse(readUrl(parsePlayerURLUUID("bba224a20bff4913b04227ca3b60973f")));
				}
				catch(Exception ex){
					connError = true;
				}
				if(playerOnline && !connError){
					return "Nicked player (100%)";
				}
				else if(connError){
					return "Connection error (100%)";
				}
				else if(!playerOnline && !connError){
					return "Player not found or nicked player (50-50%)";
				}
			}
		
		return (String) o.get("rankName");
	}
	public static String getName(String ign){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(parsePlayerURLGeneric(playername)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				boolean playerOnline = byName(ign) != null; //If the player is online, we're sure that the player is in Hive's database
				boolean connError = false;
				try{
					//RoccoDev's UUID
					parser.parse(readUrl(parsePlayerURLUUID("bba224a20bff4913b04227ca3b60973f")));
				}
				catch(Exception ex){
					connError = true;
				}
				if(playerOnline && !connError){
					return "Nicked player (100%)";
				}
				else if(connError){
					return "Connection error (100%)";
				}
				else if(!playerOnline && !connError){
					return "Player not found or nicked player (50-50%)";
				}
				
				
				
			}
		
		return (String) o.get("username");
	}
	public static NetworkPlayerInfo byName(String ign){
		for(NetworkPlayerInfo p : The5zigAPI.getAPI().getServerPlayers()) {
			
		if(p.getGameProfile().getName().equals(ign)) return p;
		}
	return null; 
	}
	public static Date lastGame(String ign, String game){
		String playername = ign;
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		
			try {
				o = (JSONObject) parser.parse(readUrl(GameParsePlayerURL(playername, game)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long time = (long) o.get("lastlogin");
			return new Date(time * 1000);
	}
	public static String getUUID(String ign){
		JSONParser parser = new JSONParser();
		JSONObject o = null;
		try {
			o = (JSONObject) parser.parse(readUrl(parseMojangPlayerAPI(ign)));
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return (String) o.get("id");
	}
	public static int getMonthlyLeaderboardsRank(String ign, String game){
		String uuid = getUUID(ign);
		JSONParser parser = new JSONParser();
		JSONArray o1 = null;
		JSONObject o2 = null;
			try {
				o1 = (JSONArray) parser.parse(((JSONObject) parser.parse(readUrl(parseMonthlyURL(game.toLowerCase())))).get("leaderboard").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}
		for(int i = 0; i<=350; i++){
	    	try {
				o2 = (JSONObject) parser.parse(o1.get(i).toString());
				} catch (Exception e) {
				return 0;
			}
	    	if(o2.get("uuid").toString().equals(uuid)){
	    		return i + 1;
	    	}
		}
		return 0;
	}
	public static String getMonthlyLeaderboardsPlayerInfo(int index, String game){
		String unit = "points";
		if(game.equals("TIMV")){
				unit = "karma";
		}	
		JSONParser parser = new JSONParser();
		JSONArray o1 = null;
		JSONObject o2 = null;
			try {
				o1 = (JSONArray) parser.parse(((JSONObject) parser.parse(readUrl(parseMonthlyURL(game.toLowerCase())))).get("leaderboard").toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				o2 = (JSONObject) parser.parse(o1.get(index).toString());
				return o2.get("name").toString() + "," + o2.get(unit).toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		return "";
	}
	public static Long getLeaderboardsPlacePoints(int index, String game){
		JSONParser parser = new JSONParser();
		JSONObject o1 = null;
		try {
			o1 = (JSONObject) parser.parse(((JSONArray) parser.parse(((JSONObject) parser.parse(readUrl(HiveAPI.parseLeaderboardPlaceURL(index, game)))).get("leaderboard").toString())).get(0).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (game) {
		default : try {
				return (Long) parser.parse(o1.get("total_points").toString());
			} catch (Exception e) {
				return null;
			}
		case "TIMV" : try {
				return (Long) parser.parse(o1.get("karma").toString());
			} catch (Exception e) {
				return null;
			}	
		}
	}

 	public static ChatColor getRankColor(String rankName){
		ChatColor rankColor = null;
		switch(rankName){
		case "Regular Hive Member": rankColor = ChatColor.BLUE;
			break;
		case "Gold Hive Member": rankColor = ChatColor.GOLD;
			break;
		case "Diamond Hive Member": rankColor = ChatColor.AQUA;
			break;
		case "Lifetime Emerald Hive Member": rankColor = ChatColor.GREEN;
			break;
		case "VIP Player": rankColor = ChatColor.DARK_PURPLE;
			break;
		case "Hive Moderator": rankColor = ChatColor.RED;
			break;
		case "Senior Hive Moderator": rankColor = ChatColor.DARK_RED;
			break;
		case "Hive Developer": rankColor = ChatColor.GRAY;
			break;
		case "Hive Founder and Owner": rankColor = ChatColor.YELLOW;
			break;
		default: rankColor = ChatColor.WHITE; //Fallback
			break;
		}
		return rankColor;
	}
	
	public static ChatColor getRankColorFromIgn(String ign){
		String rank = getNetworkRank(ign);
		return getRankColor(rank);
	}
		
	private static String readUrl(URL url) throws Exception {
	    BufferedReader reader = null;
	    try {
	       URLConnection conn = url.openConnection();
	       conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36(KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36");
	        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        
	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}

}
