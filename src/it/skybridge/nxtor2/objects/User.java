package it.skybridge.nxtor2.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.utils.GameStatus;
import it.skybridge.nxtor2.utils.Title;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class User {

	String name;
	boolean online;
	
	public User(String name) {
		this.name = name;
		this.online = (Bukkit.getPlayer(name) != null && Bukkit.getOfflinePlayer(name).isOnline());
	}
	
	public static User getUser(String name) {
		return new User(name);
	}
	
	public void sendMessage(String message) {
		if(isOnline()) {
			getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', SkyBridge.getInstance().getPrefix() + message));
		}
	}
	
	public void sendTitle(String title, ChatColor color) {
		if(isOnline()) {
			Title.sendTitle(getPlayer(), title, 5, 20, 5, color);
		}
	}
	
	public void register() {
		SkyBridge instance = SkyBridge.getInstance();
		instance.getFilesManager().getStatsFile().set(getName()+".wins", 0);
		instance.getFilesManager().getStatsFile().set(getName()+".loses", 0);
		instance.getFilesManager().getStatsFile().set(getName()+".goals", 0);
		instance.getFilesManager().getStatsFile().set(getName()+".kills", 0);
		instance.getFilesManager().getStatsFile().set(getName()+".deaths", 0);
		instance.getFilesManager().getStatsFile().set(getName()+".playedgames", 0);
		instance.getFilesManager().saveFile(instance.stats, instance.stats_file);
	}
	
	public void teleport(Location l) {
		if(isOnline()) {
			getPlayer().teleport(l);
		}
	}
	
	public void clearInv() {
		try {
		getInv().clear();
		getInv().setHelmet(new ItemStack(Material.AIR));
		getInv().setChestplate(new ItemStack(Material.AIR));
		getInv().setLeggings(new ItemStack(Material.AIR));
		getInv().setBoots(new ItemStack(Material.AIR));
		} catch(Exception ex) { ex.printStackTrace(); }
	}
	
	public void closeInv() {
		if(isOnline()) {
			getPlayer().closeInventory();
		}
	}
	
	public void respawn() {
		if(isOnline()) {
			getPlayer().spigot().respawn();
		}
	}
	
	public void openInv(Inventory inv) {
		if(isOnline()) {
			getPlayer().openInventory(inv);
		}
	}
	
	public boolean hasPermission(String permission) {
		if(isOnline()) {
			return getPlayer().hasPermission(permission);
		}
		return false;
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public boolean isPlaying() {
		if(isOnline()) {
			if(!getPlayer().getWorld().getName().equalsIgnoreCase(SkyBridge.getInstance().getConfig().getString("spawnWorld"))) {
				if(!getPlayer().getWorld().getName().contains("bridgetraining")) {
					Arena a = getArena();
					if(a.getStatus().equals(GameStatus.PLAYING)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isRegistered() {
		return SkyBridge.getInstance().getFilesManager().getStatsFile().getString(name+".wins") != null;
	}
	
	public String getName() {
		return name;
	}
	
	public Player getPlayer() {
		if(isOnline()) {
			return Bukkit.getPlayer(name);
		}
		return null;
	}
	
	public World getWorld() {
		if(isOnline()) {
			return getPlayer().getWorld();
		}
		return null;
	}
	
	public Location getLoc() {
		if(isOnline()) {
			return getPlayer().getLocation();
		}
		return null;
	}
	
	public PlayerInventory getInv() {
		if(isOnline()) {
			return getPlayer().getInventory();
		}
		return null;
	}
	
	public Arena getArena() {
		return Arena.getArenaFromWorld(getWorld());
	}
}
