package it.skybridge.nxtor2.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PickupArrowListener implements Listener {

	  @EventHandler
	  public void onPickUp(PlayerPickupItemEvent e) {
		  e.setCancelled(true);
	  }
}
