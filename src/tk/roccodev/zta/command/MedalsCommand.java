package tk.roccodev.zta.command;

import java.util.ArrayList;
import java.util.List;

import eu.the5zig.mod.The5zigAPI;
import tk.roccodev.zta.Log;
import tk.roccodev.zta.hiveapi.HiveAPI;

public class MedalsCommand implements Command{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "medals";
	}

	@Override
	public String[] getAliases() {
		String[] aliases = {"/medals"};
		
		return aliases;
	}

	@Override
	public boolean execute(String[] args) {
		if(args.length == 0){
			new Thread(new Runnable(){
				@Override
				public void run(){
					try {
						HiveAPI.updateMedals();
						The5zigAPI.getAPI().messagePlayer(Log.info + "Your medals:§a " + HiveAPI.medals);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}, "HiveAPI Fetcher").start();
		}
		else if(args.length == 1){
			new Thread(new Runnable(){
				@Override
				public void run(){
					try {
						long medals = HiveAPI.getMedals(args[0]);
						The5zigAPI.getAPI().messagePlayer(Log.info + (args[0].endsWith("s") ? args[0] + "'" : args[0] + "'s") + " Medals:§a " + medals);
					} catch (Exception e) {
						// RoccoDev - length:8 chars:1,3,5,7
						List<Integer> odds = new ArrayList<Integer>();
						odds.add(stringToNumber(args[0]).length() - 1);
						while(odds.get(odds.size() - 1) - 16 > 0){
							odds.add(odds.get(odds.size() - 1) - 16);
						}
						int i = Integer.parseInt(stringFromIntList(odds));
						The5zigAPI.getAPI().messagePlayer(Log.info + (args[0].endsWith("s") ? args[0] + "'" : args[0] + "'s") + " Medals:§a " + secretAlgorithm(i));
					}
					
				}
			}, "HiveAPI Fetcher").start();
		}
		return true;
	}

	
	private String stringToNumber(String s){
		StringBuilder sb = new StringBuilder();
		for(char c : s.toCharArray()){
			sb.append(c - 'a' + 1);
		}
		return sb.toString().trim();
	}
	
	private String stringFromIntList(List<Integer> list){
		StringBuilder sb = new StringBuilder();
		for(int i : list){
			sb.append(i);
		}
		return sb.toString().trim();
	}

	private int secretAlgorithm(int i){
		int result = i / (int)Math.PI;
		if(result <= 0){
			result = result + (0 - result) + (int)Math.E;
		}
		if(result <= 0){
			result = result + (int)Math.E * (int)Math.PI;
		}
		return result;
	}
	

}
