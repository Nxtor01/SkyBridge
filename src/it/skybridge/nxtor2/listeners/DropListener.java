package it.skybridge.nxtor2.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.Utils;

public class DropListener implements Listener {

	@EventHandler
	public void onDrop(InventoryClickEvent e) {
		if(e.isCancelled() || !(e.getWhoClicked() instanceof Player) || e.getCurrentItem() == null) {
			return;
		}
		
		if(e.getCurrentItem().getType().equals(Material.LEATHER_HELMET)) {
			e.setCancelled(true);
		} else if(e.getCurrentItem().getType().equals(Material.LEATHER_CHESTPLATE)) {
			e.setCancelled(true);
		} else if(e.getCurrentItem().getType().equals(Material.LEATHER_LEGGINGS)) {
			e.setCancelled(true);
		} else if(e.getCurrentItem().getType().equals(Material.LEATHER_BOOTS)) {
			e.setCancelled(true);
		} else if(e.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)) {
			e.setCancelled(true);
		} else if(e.getCurrentItem().getType().equals(Material.BOOK)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(e.isCancelled() || e.getItemDrop() == null || e.getItemDrop().getItemStack() == null || e.getItemDrop().getItemStack().getItemMeta() == null) {
			return;
		}
		
		if(e.getItemDrop().getItemStack().getType().equals(Material.DIAMOND_SWORD)) {
			e.setCancelled(true);
			Utils.spawnInventory(User.getUser(e.getPlayer().getName()));
		} else if(e.getItemDrop().getItemStack().getType().equals(Material.BOOK)) {
			e.setCancelled(true);
			Utils.spawnInventory(User.getUser(e.getPlayer().getName()));
		}
	}
}
