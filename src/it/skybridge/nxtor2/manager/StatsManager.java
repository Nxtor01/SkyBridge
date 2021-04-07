package it.skybridge.nxtor2.manager;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.User;

public class StatsManager {

	SkyBridge instance;
	
	public StatsManager(SkyBridge instance) {
		this.instance = instance;
	}
	
	public void addGame() {
		int played = getGames();
		instance.getFilesManager().getStatsFile().set("played_games", Math.abs(played+1));
		instance.getFilesManager().saveFile(instance.stats, instance.stats_file);
	}
	
	public int getGames() {
		return SkyBridge.getInstance().getFilesManager().getStatsFile().getInt("played_games");
	}
	
	public void addWin(User u) {
		instance.getFilesManager().getStatsFile().set(u.getName()+".wins", Math.abs(getWins(u)+1));
		instance.getFilesManager().saveFile(instance.stats, instance.stats_file);
	}
	
	public int getWins(User u) {
		if(u.isRegistered()) {
		return instance.getFilesManager().getStatsFile().getInt(u.getName() + ".wins");
		} else {
			return 0;
		}
	}
	
	public void addLose(User u) {
		instance.getFilesManager().getStatsFile().set(u.getName()+".loses", Math.abs(getLoses(u)+1));
		instance.getFilesManager().saveFile(instance.stats, instance.stats_file);
	}
	
	public int getLoses(User u) {
		if(u.isRegistered()) {
		return instance.getFilesManager().getStatsFile().getInt(u.getName() + ".loses");
		} else {
			return 0;
		}
	}
	
	public void addGoal(User u) {
		instance.getFilesManager().getStatsFile().set(u.getName()+".goals", Math.abs(getKills(u)+1));
		instance.getFilesManager().saveFile(instance.stats, instance.stats_file);
	}
	
	public int getGoals(User u) {
		if(u.isRegistered()) {
		return instance.getFilesManager().getStatsFile().getInt(u.getName() + ".goals");
		} else {
			return 0;
		}
	}
	
	public void addKill(User u) {
		instance.getFilesManager().getStatsFile().set(u.getName()+".kills", Math.abs(getKills(u)+1));
		instance.getFilesManager().saveFile(instance.stats, instance.stats_file);
	}
	
	public int getKills(User u) {
		if(u.isRegistered()) {
		return instance.getFilesManager().getStatsFile().getInt(u.getName() + ".kills");
		} else {
			return 0;
		}
	}
	
	public void addDeath(User u) {
		instance.getFilesManager().getStatsFile().set(u.getName()+".deaths", Math.abs(getDeaths(u)+1));
		instance.getFilesManager().saveFile(instance.stats, instance.stats_file);
	}
	
	public int getDeaths(User u) {
		if(u.isRegistered()) {
		return instance.getFilesManager().getStatsFile().getInt(u.getName() + ".deaths");
		} else {
			return 0;
		}
	}
	
	public void addPlayedGame(User u) {
		instance.getFilesManager().getStatsFile().set(u.getName()+".playedgames", Math.abs(getPlayedGames(u)+1));
		instance.getFilesManager().saveFile(instance.stats, instance.stats_file);
	}
	
	public int getPlayedGames(User u) {
		if(u.isRegistered()) {
		return instance.getFilesManager().getStatsFile().getInt(u.getName() + ".playedgames");
		} else {
			return 0;
		}
	}
}
