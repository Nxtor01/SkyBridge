package it.skybridge.nxtor2.objects;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;

import net.md_5.bungee.api.ChatColor;

public class Team {

	List<User> team;
	Color c;
	
	public Team(List<User> team, Color c) {
		this.team = team;
		this.c = c;
	}
	
	public static Team getTeam(List<User> team, Color c) {
		return new Team(team, c);
	}
	
	public void sendMessage(String message) {
		for(User u : team) {
			if(u.isOnline()) {
				u.sendMessage(message);
			}
		}
	}
	
	public void sendTitle(String title, ChatColor c) {
		for(User u : team) {
			if(u.isOnline()) {
				u.sendTitle(title, c);
			}
		}
	}
	
	public void teleport(Location l) {
		for(User u : team) {
			if(u.isOnline()) {
				u.teleport(l);
			}
		}
	}
	
	public boolean equals(Team team) {
		return (this.getTeam().equals(team.getTeam()));
	}
	
	public List<User> getTeam() {
		return team;
	}
	
	public Color getColor() {
		return c;
	}
}
