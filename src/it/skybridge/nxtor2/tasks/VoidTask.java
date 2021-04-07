package it.skybridge.nxtor2.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.Utils;

public class VoidTask implements Runnable {

	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			User u = User.getUser(p.getName());
			Location l = u.getLoc();
			if(u.isPlaying()) {
				Arena a = u.getArena();
				if(l.getY() <= a.getYVoid()) {
					Color c;
					if(a.getTeamBlue().getTeam().contains(u)) {
						c = Color.BLUE;
					} else {
						c = Color.RED;
					}
					Utils.teleportToUnderMap(u, c, a);
					SkyBridge.getInstance().getStatsManager().addDeath(u);
					Utils.inventory(u, c);
					EntityDamageEvent e = p.getLastDamageCause();
					DamageCause dc = p.getLastDamageCause().getCause();
					if(dc.equals(DamageCause.ENTITY_ATTACK) || dc.equals(DamageCause.PROJECTILE)) {
						if(((LivingEntity) e.getEntity()).getKiller() != null && e.getEntity() instanceof Player) {
							User killer = User.getUser(((LivingEntity) e.getEntity()).getKiller().getName());
							SkyBridge.getInstance().getStatsManager().addKill(killer);
							   Color ec;
							   if(c.equals(Color.RED)) {
								   ec = Color.BLUE;
							   } else {
								   ec = Color.RED;
							   }
							a.getTeams().forEach(team -> {
								team.sendMessage(Utils.convertColor(ec) + killer.getName() + " §7ha ucciso " + Utils.convertColor(c) + u.getName());
							});
						}
					} else {
						a.getTeams().forEach(team -> {
							team.sendMessage(Utils.convertColor(c) + u.getName() + " §7e' caduto nel vuoto.");
						});
					}
				}
			} else {
				World w = p.getWorld();
				if(!w.getName().contains("bridgetraining")) {
					if(!w.getName().equalsIgnoreCase(SkyBridge.getInstance().getConfig().getString("spawnWorld"))) {
						Arena a = u.getArena();
						if(l.getY() <= a.getYVoid()) {
							Utils.teleportToUnderMap(u, Color.RED, a);
						    p.setFallDistance(0.0F);
						}
					}
				}
			}
		}
	}

}
