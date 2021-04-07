package it.skybridge.nxtor2.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.User;

public class ArrowListener implements Listener {

	@EventHandler
	public void onArrowHit(ProjectileHitEvent e) {
		
	    if (!(e.getEntity() instanceof Arrow)) {
	        return;
	    }
	    
	      Arrow arrow = (Arrow) e.getEntity();
	      
	      if (!(arrow.getShooter() instanceof Player)) {
	        return;
	      }
	      
	      Player p = (Player)arrow.getShooter();
	      User u = User.getUser(p.getName());
	      
	      if(!u.isPlaying()) {
	    	  return;
	      }
	      
	      arrow.remove();
	}
	
	  @EventHandler
	  public void onShoot(ProjectileLaunchEvent e) {
	    if (!(e.getEntity() instanceof Arrow)) {
	      return;
	    }
	    
	    Arrow arrow = (Arrow) e.getEntity();
	    
	    if (!(arrow.getShooter() instanceof Player)) {
	      return;
	    }
	    Player p = (Player)arrow.getShooter();
	    User u = User.getUser(p.getName());
	    new BukkitRunnable() {
	        int seconds = 4;


	        @Override
	        public void run() {
	          if (this.seconds == 0 && u.isPlaying()) {
	            p.getInventory().setItem(8, new ItemStack(Material.ARROW, 1));
	            p.setLevel(0);
	            cancel();
	            
	            return;
	          } 
	          p.setLevel(this.seconds);
	          
	          this.seconds--;
	        }
	      }.runTaskTimerAsynchronously(SkyBridge.getInstance(), 0L, 20L);
	  }
}
