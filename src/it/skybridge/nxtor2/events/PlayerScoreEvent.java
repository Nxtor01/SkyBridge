package it.skybridge.nxtor2.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.Team;
import it.skybridge.nxtor2.objects.User;

public class PlayerScoreEvent extends Event {

	private static final HandlerList HANDLERS_LIST = new HandlerList();

	Arena a;
	User u;
	Team scorer;
	
	public PlayerScoreEvent(Arena a, User u, Team scorer) {
		this.a = a;
		this.u = u;
		this.scorer = scorer;
	}
	
	public Arena getArena() {
		return a;
	}
	
	public User getScorer() {
		return u;
	}
	
	public Team getScorerTeam() {
		return scorer;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}
	
}
