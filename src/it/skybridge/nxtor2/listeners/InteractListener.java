package it.skybridge.nxtor2.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.GUI;
import it.skybridge.nxtor2.utils.GameStatus;
import net.md_5.bungee.api.ChatColor;

public class InteractListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		User u = User.getUser(e.getPlayer().getName());
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if(u.getWorld().getName().equalsIgnoreCase(SkyBridge.getInstance().getConfig().getString("spawnWorld"))) {
				if(e.getItem() != null) {
				ItemStack is = e.getItem();
				if(is.getType().equals(Material.DIAMOND_SWORD)) {
					u.openInv(new GUI().getGUI());
					return;
				} else if(is.getType().equals(Material.BOOK)) {
					u.sendMessage("Statistiche di &e" + u.getName() + ":" + '\n'
			                + "&7Partite giocate: &b" + SkyBridge.getInstance().getStatsManager().getPlayedGames(u) + '\n'
				            + "&7Vittorie: &b" + SkyBridge.getInstance().getStatsManager().getWins(u) + '\n'
				            + "&7Sconfitte: &b" + SkyBridge.getInstance().getStatsManager().getLoses(u) + '\n'
				            + "&7Goals: &b" + SkyBridge.getInstance().getStatsManager().getGoals(u) + '\n'
				            + "&7Uccisioni: &b" + SkyBridge.getInstance().getStatsManager().getKills(u) + '\n'
				            + "&7Morti: &b" + SkyBridge.getInstance().getStatsManager().getDeaths(u));
					return;
				} else if(is.getType().equals(Material.RED_ROSE)) {
					Arena a = u.getArena();
					a.exitGame(u);
					return;
				}
				}
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.isCancelled() || e.getClickedInventory() == null || e.getCurrentItem() == null) {
			return;
		}

		User u = User.getUser(e.getWhoClicked().getName());
		
		if(!u.isPlaying()) {
			Inventory inv = e.getClickedInventory();
			if(inv.getTitle().equalsIgnoreCase("§ePartite disponibili")) {
				ItemMeta im = e.getCurrentItem().getItemMeta();
				String wline;
				try {
				  wline = im.getLore().get(2);
				} catch(Exception ex) { wline = "undefined"; }
				String wline2 = ChatColor.stripColor(wline);
				String world = wline2.substring(8);
				if(world.equalsIgnoreCase("d")) {
					u.closeInv();
					u.sendMessage("&cSi e' verificato un errore, riprova.");
					return;
				}
				World w = Bukkit.getWorld(world);
				Arena a = Arena.getArenaFromWorld(w);
				if(a.getStatus().equals(GameStatus.WAITING)) {
					u.closeInv();
					a.joinGame(u);
				} else {
					u.closeInv();
					u.sendMessage("&7Questa mappa e' gia' in uso.");
					return;
				}
			}
			return;
		}
		
	    if (e.getSlotType() != InventoryType.SlotType.ARMOR)
	        return;
	    
	    e.setCancelled(true);
	}
}
