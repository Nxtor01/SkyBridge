package it.skybridge.nxtor2.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import it.skybridge.nxtor2.objects.User;

public class FoodListener implements Listener {

	  @EventHandler
	  public void onHunger(FoodLevelChangeEvent e) {
		  e.setCancelled(true);
	  }
	  
	  @EventHandler
	  public void onEat(PlayerItemConsumeEvent e) {
		  Player p = e.getPlayer();
		  User u = User.getUser(p.getName());
		  
		  if(!u.isPlaying()) {
			  return;
		  }
		  
		  if (e.getItem().getType() != Material.GOLDEN_APPLE) {
		      return;
		    }
		    if (e.getItem().getAmount() == 1) {
		      p.setItemInHand(null);
		    } else {
		      ItemStack itemStack = e.getItem().clone();
		      itemStack.setAmount(e.getItem().getAmount() - 1);
		      p.setItemInHand(itemStack);
		    } 
		    
		    p.getActivePotionEffects().forEach(paramPotionEffect -> p.removePotionEffect(paramPotionEffect.getType()));
		    if(!(p.getHealth() <= 4.0D)) {
			    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0));
		    }
		    p.setHealth(20.0D);
		    e.setCancelled(true);
	  }
}
