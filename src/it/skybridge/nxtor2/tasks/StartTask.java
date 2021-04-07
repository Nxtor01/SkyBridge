package it.skybridge.nxtor2.tasks;

import java.util.List;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.User;

public class StartTask implements Runnable {

	List<User> users;
	Arena a;
	int players;
	
	public StartTask(List<User> users, Arena a) {
		this.users = users;
		this.players = users.size();
		this.a = a;
	}
	
	@Override
	public void run() {
		World w = a.getWorld();
		new BukkitRunnable() {
			@Override
			public void run() {
				if(w.getPlayers().size() >= players) {
				users.forEach(u -> u.sendMessage("Il gioco iniziera' tra &e5 &7secondi!"));
				} else {
					users.forEach(u -> u.sendMessage("&cGioco annullato per mancanza di giocatori!"));	
					this.cancel();
				}
			}
		}.runTaskLaterAsynchronously(SkyBridge.getInstance(), 10);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(w.getPlayers().size() >= players) {
				users.forEach(u -> u.sendMessage("Il gioco iniziera' tra &e4 &7secondi!"));
				} else {
					this.cancel();
				}
			}
		}.runTaskLaterAsynchronously(SkyBridge.getInstance(), 30);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(w.getPlayers().size() >= players) {
				users.forEach(u -> u.sendMessage("Il gioco iniziera' tra &e3 &7secondi!"));
				} else {
					this.cancel();
				}
			}
		}.runTaskLaterAsynchronously(SkyBridge.getInstance(), 50);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(w.getPlayers().size() >= players) {
				users.forEach(u -> u.sendMessage("Il gioco iniziera' tra &e2 &7secondi!"));
				} else {
					this.cancel();
				}
			}
		}.runTaskLaterAsynchronously(SkyBridge.getInstance(), 70);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(w.getPlayers().size() >= players) {
				users.forEach(u -> u.sendMessage("Il gioco iniziera' tra &e1 &7secondo!"));
				} else {
					this.cancel();
				}
			}
		}.runTaskLaterAsynchronously(SkyBridge.getInstance(), 90);
		new BukkitRunnable() {
			@Override
			public void run() {
				if(w.getPlayers().size() >= players) {
				a.startGame();
				} else {
					this.cancel();
				}
			}
		}.runTaskLaterAsynchronously(SkyBridge.getInstance(), 110);
	}
}
