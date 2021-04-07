package it.skybridge.nxtor2.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.inventivegames.hologram.Hologram;
import de.inventivegames.hologram.HologramAPI;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.events.PlayerScoreEvent;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.Team;
import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.Utils;

public class PlayerScoreListener implements Listener {

	@EventHandler
	public void onScore(PlayerScoreEvent e) {
		Arena a = e.getArena();
		User u = e.getScorer();
		
		int goals;
		Team team = e.getScorerTeam();
		boolean b = team.getColor().equals(Color.BLUE);
		
		
		
		if(b) {
			goals = a.getGoals().get(1);
		} else {
			goals = a.getGoals().get(0);
		}
		
		FileConfiguration fc = SkyBridge.getInstance().getFilesManager().getArenasFile();
		double x;
		double y;
		double z;
		if(b) {
			 x = fc.getDouble("arenas."+a.getWorld().getName()+".hblueX");
			 y = fc.getDouble("arenas."+a.getWorld().getName()+".hblueY");
			 z = fc.getDouble("arenas."+a.getWorld().getName()+".hblueZ");
		} else {
			x = fc.getDouble("arenas."+a.getWorld().getName()+".hredX");
			y = fc.getDouble("arenas."+a.getWorld().getName()+".hredY");
			z = fc.getDouble("arenas."+a.getWorld().getName()+".hredZ");
		}
		Location l = new Location(a.getWorld(), x, y, z);
		List<Hologram> h = new ArrayList<>();
		HologramAPI.getHolograms().forEach(holo -> {
			if(holo.getLocation().equals(l)) {
				h.add(holo);
			}
		});
		
		Hologram holo = h.get(0);

		if(b) {
			holo.setText("&7Squadra &9Blu&7, Goal:&b " + Math.abs(goals+1));
		} else {
			holo.setText("&7Squadra &cRossa&7, Goal:&b " + Math.abs(goals+1));
		}

		SkyBridge.getInstance().getStatsManager().addGoal(u);
		
		for(User us : a.getTeamRed().getTeam()) {
		BPlayerBoard board = Netherboard.instance().getBoard(us.getPlayer());
		board.setAll(
				" ",
				"§7Team §cRosso §8» §b"+a.getGoals().get(0)+"§7 Goal",
				"§7Team §9Blu §8» §b"+a.getGoals().get(1)+"§7 Goal",
				"  ",
				"§7Goal della tua squadra §8» §b0",
				"   ",
				"§7Mappa §8» §b" + a.getName(),
				"§7Modalita' §8» §b"+a.getMode()
				);
		}

		for(User us : a.getTeamBlue().getTeam()) {
		BPlayerBoard board = Netherboard.instance().getBoard(us.getPlayer());
		board.setAll(
				" ",
				"§7Team §cRosso §8» §b"+a.getGoals().get(0)+"§7 Goal",
				"§7Team §9Blu §8» §b"+a.getGoals().get(1)+"§7 Goal",
				"  ",
				"§7Goal della tua squadra §8» §b0",
				"   ",
				"§7Mappa §8» §b" + a.getName(),
				"§7Modalita' §8» §b"+a.getMode()
				);
		}
		
		
		if(team.getColor().equals(Color.BLUE)) {
			if(a.getGoals().get(1) >= 5) {
				a.finishGame(team);
				return;
			}
		} else {
			if(a.getGoals().get(0) >= 5) {
				a.finishGame(team);
				return;
			}
		}
		Utils.runCage(a, u);
	}
}
