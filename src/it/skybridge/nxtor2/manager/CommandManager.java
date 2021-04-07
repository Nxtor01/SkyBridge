package it.skybridge.nxtor2.manager;

import java.util.logging.Level;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.commands.SkyBridgeCommand;

public class CommandManager {

	SkyBridge instance;
	
	public CommandManager(SkyBridge instance) {
		this.instance = instance;
	}
	
	public void setupCommands() {
		instance.log(Level.INFO, "[SkyBridge] Registering commands..");
		instance.getCommand("skybridge").setExecutor(new SkyBridgeCommand());
	}
}
