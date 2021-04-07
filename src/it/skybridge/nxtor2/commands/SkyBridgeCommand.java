package it.skybridge.nxtor2.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.objects.User;
import it.skybridge.nxtor2.utils.Utils;

public class SkyBridgeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("skybridge")) {
			if(sender instanceof Player) {
				User p = User.getUser(sender.getName());
				if(p.hasPermission("skybridge.admin")) {
					int length = args.length;
					if(length == 0) {
					p.sendMessage("&7Comandi disponibili:" + '\n'
								+ "&7- &e/skybridge createmap" + '\n'
								+ "&7- &e/skybridge setlobby" + '\n'
								+ "&7- &e/skybridge statistiche" + '\n'
								+ "&7- &e/skybridge games");
					return true;
					} else if(length > 0) {
						String arg = args[0];
						if(arg.equalsIgnoreCase("createmap")) {
							// TODO
							return true;
						} else if(arg.equalsIgnoreCase("games")) {
							p.sendMessage("Le partite giocate sono state: &b" + SkyBridge.getInstance().getStatsManager().getGames());
							return true;
						} else if(arg.equalsIgnoreCase("setlobby")) {
							try {
								Utils.setLobby(p);
							} catch(Exception ex) { ex.printStackTrace(); p.sendMessage("&4ERRORE, &cguarda la console per dettagli."); return true; }
							p.sendMessage("Lobby impostata con successo");
							return true;
						} else if(arg.equalsIgnoreCase("statistiche")) {
							p.sendMessage("Statistiche di &e" + p.getName() + ":" + '\n'
						                + "&7Partite giocate: &b" + SkyBridge.getInstance().getStatsManager().getPlayedGames(p) + '\n'
							            + "&7Vittorie: &b" + SkyBridge.getInstance().getStatsManager().getWins(p) + '\n'
							            + "&7Sconfitte: &b" + SkyBridge.getInstance().getStatsManager().getLoses(p) + '\n'
							            + "&7Goals: &b" + SkyBridge.getInstance().getStatsManager().getGoals(p) + '\n'
							            + "&7Uccisioni: &b" + SkyBridge.getInstance().getStatsManager().getKills(p) + '\n'
							            + "&7Morti: &b" + SkyBridge.getInstance().getStatsManager().getDeaths(p));
							return true;
						}
					}
				} else {
					if(args.length == 0) {
					p.sendMessage("&bSkyClouds &f2.0 &7usa &eSkyBridge &7da &eNxtor2_");
					} else if(args.length > 0) {
						String arg = args[0];
						if(arg.equalsIgnoreCase("statistiche")) {
							p.sendMessage("Statistiche di &e" + p.getName() + ":" + '\n'
						                + "&7Partite giocate: &b" + SkyBridge.getInstance().getStatsManager().getPlayedGames(p) + '\n'
							            + "&7Vittorie: &b" + SkyBridge.getInstance().getStatsManager().getWins(p) + '\n'
							            + "&7Sconfitte: &b" + SkyBridge.getInstance().getStatsManager().getLoses(p) + '\n'
							            + "&7Goals: &b" + SkyBridge.getInstance().getStatsManager().getGoals(p) + '\n'
							            + "&7Uccisioni: &b" + SkyBridge.getInstance().getStatsManager().getKills(p) + '\n'
							            + "&7Morti: &b" + SkyBridge.getInstance().getStatsManager().getDeaths(p));
							return true;
						}
					}
				}
			} else {
				sender.sendMessage(SkyBridge.getInstance().getPrefix() + "§cNon sei un giocatore!");
				return true;
			}
		}
		return false;
	}

}
