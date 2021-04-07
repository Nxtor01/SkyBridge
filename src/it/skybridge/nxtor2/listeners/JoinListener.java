package it.skybridge.nxtor2.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.Utils;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		User u = User.getUser(e.getPlayer().getName());
		if(!u.isRegistered()) {
			u.register();
		}
		u.teleport(Utils.getLocationSpawn());
		Utils.spawnInventory(u);
	}
}
