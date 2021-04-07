package it.skybridge.nxtor2.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.Arena;
import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.Utils;

public class QuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		User u = User.getUser(e.getPlayer().getName());
		if(u.isPlaying()) {
			Arena a = u.getArena();
			a.getTeams().forEach(team -> {
				team.sendMessage("§b" + a.getName() + " §7si e' disconnesso.");
			});
			SkyBridge.getInstance().getStatsManager().addPlayedGame(u);
			SkyBridge.getInstance().getStatsManager().addLose(u);
			if(a.getTeamBlue().getTeam().size() <= 0) {
				a.finishGame(a.getTeamRed());
			} else if(a.getTeamRed().getTeam().size() <= 0) {
				a.finishGame(a.getTeamBlue());
			}
		}
		u.clearInv();
		u.teleport(Utils.getLocationSpawn());
	}
}
