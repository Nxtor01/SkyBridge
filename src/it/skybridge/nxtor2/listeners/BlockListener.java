package it.skybridge.nxtor2.listeners;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.GameStatus;
import it.skybridge.nxtor2.utils.Utils;

public class BlockListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		User u = User.getUser(e.getPlayer().getName());
		Block b = e.getBlock();
		String wn = b.getLocation().getWorld().getName();
		if(wn.equalsIgnoreCase(SkyBridge.getInstance().getConfig().getString("spawnWorld"))) {
			if(!u.getPlayer().hasPermission("skybridge.*")) {
			e.setCancelled(true);
			return;
			}
		} else if(wn.contains("bridgetraining")) {
			return;
		}
		
			Arena a = u.getArena();
			if(a.getStatus().equals(GameStatus.PLAYING)) {
			if(a.getPlacedBlocks().isEmpty() || !a.getPlacedBlocks().contains(b.getLocation())) {
				e.setCancelled(true);
				u.sendMessage("Non puoi rompere questo blocco!");
				return;
			}
			} else { 
				e.setCancelled(true);
				return;
			}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		User u = User.getUser(e.getPlayer().getName());
		Block b = e.getBlock();
		String wn = b.getLocation().getWorld().getName();
		if(wn.equalsIgnoreCase(SkyBridge.getInstance().getConfig().getString("spawnWorld"))) {
			if(!u.getPlayer().hasPermission("skybridge.*")) {
			e.setCancelled(true);
			return;
			}
		} else if(wn.contains("bridgetraining")) {
			return;
		}
		
		if(u.isPlaying()) {
			Arena a = u.getArena();
			new BukkitRunnable() {
				@Override
				public void run() {
			double x1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redpos1X");
			double y1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redpos1Y");
			double z1 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redpos1Z");
			double x2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluepos1X");
			double y2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluepos1Y");
			double z2 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluepos1Z");
			
			double x12 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redpos2X");
			double y12 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redpos2Y");
			double z12 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".redpos2Z");
			double x22 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluepos2X");
			double y22 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluepos2Y");
			double z22 = SkyBridge.getInstance().getFilesManager().getArenasFile().getDouble("arenas."+a.getWorld().getName()+".bluepos2Z");
			
			Location redpos1 = new Location(a.getWorld(), x1, y1, z1);
			Location redpos2 = new Location(a.getWorld(), x12, y12, z12);
			Location bluepos1 = new Location(a.getWorld(), x2, y2, z2);
			Location bluepos2 = new Location(a.getWorld(), x22, y22, z22);
			
			List<Block> blocks = Utils.getBlocks(redpos1, redpos2);
			List<Block> blocks2 = Utils.getBlocks(bluepos1, bluepos2);
			
			if(blocks.contains(b)) {
				e.setCancelled(true);
				return;
			} else if(blocks2.contains(b)) {
				e.setCancelled(true);
				return;
			}
			
			a.addPlacedBlock(b);
				}
			}.runTaskAsynchronously(SkyBridge.getInstance());
		} else {
			if(b.getType().equals(Material.RED_ROSE)) {
				e.setCancelled(true);
				return;
			}
			if(!u.hasPermission("skybridge.*")) {
			e.setCancelled(true);
			}
		}
	}
}
