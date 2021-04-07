package it.skybridge.nxtor2.listeners;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.Utils;

public class DamageListener implements Listener {

   @EventHandler
   public void onDamage(EntityDamageEvent e) {
	   if(e.isCancelled()) {
		   return;
	   }
	   
    if (!(e.getEntity() instanceof Player)) {
	      return;
	    }
	    Player p = (Player) e.getEntity();
	    User u = User.getUser(p.getName());
	   
	    
	    if(u.isPlaying()) {
	    	if(e.getCause().equals(DamageCause.FALL)) {
	    		e.setCancelled(true);
	    	}
	    	return;
	    }
	    
	    e.setCancelled(true);
	  }
   
   @EventHandler
   public void onDamaged(EntityDamageByEntityEvent e) {
	   if(e.isCancelled()) {
		   return;
	   }
	   
    if (!(e.getEntity() instanceof Player)) {
	      return;
	    }
    
    if(!(e.getDamager() instanceof Player)) {
    	return;
    }

    Player p = (Player) e.getEntity();
    Player killer = (Player) e.getDamager();
    User u = User.getUser(p.getName());
    User ukiller = User.getUser(killer.getName());
    
    if(u.isPlaying() && ukiller.isPlaying()) {
    	Arena a = u.getArena();
    	if(a.getTeams().get(1).getTeam().contains(u) && a.getTeams().get(1).getTeam().contains(ukiller)) {
    		e.setCancelled(true);
    		return;
    	} else {
        	if(a.getTeams().get(0).getTeam().contains(u) && a.getTeams().get(0).getTeam().contains(ukiller)) {
        		e.setCancelled(true);
        		return;
        	}
    	}
    }
   }
   
   @EventHandler
   public void onDeath(PlayerDeathEvent e) {
	   Player p = e.getEntity();
	   User u = User.getUser(p.getName());
	   
	   if(u.isPlaying()) {
		   e.setDroppedExp(0);
		   e.getDrops().clear();
		   e.setDeathMessage(null);
		   Arena a = u.getArena();
		   Color c;
			if(a.getTeamBlue().getTeam().contains(u)) {
				c = Color.BLUE;
			} else {
				c = Color.RED;
			}
			SkyBridge.getInstance().getStatsManager().addDeath(u);
		   if(e.getEntity().getKiller() != null && !e.getEntity().getKiller().getName().equalsIgnoreCase(u.getName())) {
			   Player killer = e.getEntity().getKiller();
			   User ukiller = User.getUser(killer.getName());
			   Color ec;
			   if(c.equals(Color.RED)) {
				   ec = Color.BLUE;
			   } else {
				   ec = Color.RED;
			   }
				SkyBridge.getInstance().getStatsManager().addKill(ukiller);
				   a.getTeams().forEach(team -> {
					   team.sendMessage(Utils.convertColor(ec)+ukiller.getName()+" &7ha ucciso " + Utils.convertColor(c) + u.getName());
				   });
		   } else {
			   a.getTeams().forEach(team -> {
				   team.sendMessage(Utils.convertColor(c)+u.getName()+" &7e' morto.");
			   });
		   }

		   SkyBridge.getInstance().getRespawns().put(u, a);
		   SkyBridge.getInstance().getRespawns2().put(u, c);
		   new BukkitRunnable() {
			   @Override
			   public void run() {
					if(p != null) {
						if (p.isDead()) {
				   u.respawn();
						}
					}
		   }
		   }.runTaskLater(SkyBridge.getInstance(), 40L);
	   }
   }
   
   @EventHandler
   public void onRespawn(PlayerRespawnEvent e) {
	   User u = User.getUser(e.getPlayer().getName());
	   if(!SkyBridge.getInstance().getRespawns().containsKey(u)) {
		   return;
	   }
	   
	    Arena a = SkyBridge.getInstance().getRespawns().get(u);
	    Color c = SkyBridge.getInstance().getRespawns2().get(u);
		Utils.teleportToUnderMap(u, c, a);
		Utils.inventory(u, c);
		SkyBridge.getInstance().getRespawns().remove(u);
		SkyBridge.getInstance().getRespawns2().remove(u);
   }
}
