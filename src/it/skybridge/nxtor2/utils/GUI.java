package it.skybridge.nxtor2.utils;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import it.skybridge.nxtor2.objects.Arena;

@SuppressWarnings("deprecation")
public class GUI {

	public Inventory getGUI() {
		Inventory inv = Bukkit.createInventory(null, 54, "§ePartite disponibili");
		int i = 0;
		for(Arena a : Arena.getGames()) {
			if(a.getStatus().equals(GameStatus.WAITING)) {
				if(check(a)) {
					ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData());
					ItemMeta im = is.getItemMeta();
					
					im.setDisplayName("§e"+a.getName());
					
					String type = "Non definito";
					int maxplayers = 0;
					
					if(a.getType().equals(Type.SOLO)) {
						type = "Solo";
						maxplayers = 2;
					} else if(a.getType().equals(Type.DUO)) {
						type = "Duo";
						maxplayers = 4;
					} else if(a.getType().equals(Type.TRIPLO)) {
						type = "Trio";
						maxplayers = 6;
					} else if(a.getType().equals(Type.QUADRUPLO)) {
						type = "Quadruplo";
						maxplayers = 8;
					}
					
					im.setLore(Arrays.asList("§7Tipo §8»§b " + type, "§7Giocatori §8»§b " + a.getWorld().getPlayers().size()+"§7/§b"+maxplayers,"§7Mondo §8»§b " + a.getWorld().getName()));
					is.setItemMeta(im);
					inv.setItem(i, is);
					i = Math.abs(i+1);
				}
			}
		}
		return inv;
	}
	
	private boolean check(Arena a) {
		Type type = a.getType();
		World w = a.getWorld();
		if(type.equals(Type.DUO) && w.getPlayers().size() >= 4) {
			return false;
		} else if(type.equals(Type.TRIPLO) && w.getPlayers().size() >= 6) {
			return false;
		} else if(type.equals(Type.QUADRUPLO) && w.getPlayers().size() >= 8) {
			return false;
		} else if(type.equals(Type.SOLO) && w.getPlayers().size() >= 2) {
			return false;
		}
		
		return true;
	}
}
