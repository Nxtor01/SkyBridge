package it.skybridge.nxtor2.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.events.PlayerScoreEvent;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.Team;
import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.GameStatus;
import it.skybridge.nxtor2.utils.Utils;

public class PortalListener implements Listener {

	@EventHandler
	public void onPortal(PlayerPortalEvent e) {
	    Player p = e.getPlayer();
	    User u = User.getUser(p.getName());
	   
	    if(!u.isPlaying())
	    	return;
	    
	    Location l = e.getFrom();
	    Arena a = Arena.getArenaFromWorld(l.getWorld());
	      
	    Team scorer;
	    
	    if(a.getTeamBlue().getTeam().contains(u)) {
	    	scorer = a.getTeamBlue();
	    } else {
	    	scorer = a.getTeamRed();
	    }
	    
	    if (!(a.getStatus().equals(GameStatus.PLAYING)))
	        return; 
	    
	    if (e.getCause() != PlayerTeleportEvent.TeleportCause.END_PORTAL)
	      return;

		e.setCancelled(true);
		
		FileConfiguration fc = SkyBridge.getInstance().getFilesManager().getArenasFile();
		double x1; 
		double y1; 
		double z1; 
		double x2; 
		double y2; 
		double z2; 
		
		if(scorer.getColor().equals(Color.BLUE)) {
			x1 = fc.getDouble("arenas."+a.getWorld().getName()+".pblueX1");
			y1 = fc.getDouble("arenas."+a.getWorld().getName()+".pblueY1");
			z1 = fc.getDouble("arenas."+a.getWorld().getName()+".pblueZ1");
			x2 = fc.getDouble("arenas."+a.getWorld().getName()+".pblueX2");
			y2 = fc.getDouble("arenas."+a.getWorld().getName()+".pblueY2");
			z2 = fc.getDouble("arenas."+a.getWorld().getName()+".pblueZ2");
		} else {
			x1 = fc.getDouble("arenas."+a.getWorld().getName()+".predX1");
			y1 = fc.getDouble("arenas."+a.getWorld().getName()+".predY1");
			z1 = fc.getDouble("arenas."+a.getWorld().getName()+".predZ1");
			x2 = fc.getDouble("arenas."+a.getWorld().getName()+".predX2");
			y2 = fc.getDouble("arenas."+a.getWorld().getName()+".predY2");
			z2 = fc.getDouble("arenas."+a.getWorld().getName()+".predZ2");
		}
		
		Location l1 = new Location(l.getWorld(), x1, y1, z1);
		Location l2 = new Location(l.getWorld(), x2, y2, z2);
		
		for(Block b : Utils.getBlocks(l1, l2)) {
			Location loc = b.getLocation();
			if(l.equals(loc)) {
				u.sendMessage("&7Non puoi saltare nel tuo stesso portale.");
				return;
			}
		}
		
	    Bukkit.getPluginManager().callEvent(new PlayerScoreEvent(a, u, scorer));
	}
}
