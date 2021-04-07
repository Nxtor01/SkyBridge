package it.skybridge.nxtor2.manager;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import it.skybridge.nxtor2.SkyBridge;
import it.skybridge.nxtor2.listeners.ArrowListener;
import it.skybridge.nxtor2.listeners.BlockListener;
import it.skybridge.nxtor2.listeners.DamageListener;
import it.skybridge.nxtor2.listeners.DropListener;
import it.skybridge.nxtor2.listeners.FoodListener;
import it.skybridge.nxtor2.listeners.InteractListener;
import it.skybridge.nxtor2.listeners.JoinListener;
import it.skybridge.nxtor2.listeners.PickupArrowListener;
import it.skybridge.nxtor2.listeners.PlayerScoreListener;
import it.skybridge.nxtor2.listeners.PortalListener;
import it.skybridge.nxtor2.listeners.QuitListener;

public class ListenerManager {

	SkyBridge instance;
	
	public ListenerManager(SkyBridge instance) {
		this.instance = instance;
	}
	
	public void setupListeners() {
		instance.log(Level.INFO, "[SkyBridge] Registering listeners..");
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new QuitListener(), instance);
		pm.registerEvents(new JoinListener(), instance);
		pm.registerEvents(new InteractListener(), instance);
		pm.registerEvents(new FoodListener(), instance);
		pm.registerEvents(new PortalListener(), instance);
		pm.registerEvents(new PlayerScoreListener(), instance);
		pm.registerEvents(new ArrowListener(), instance);
		pm.registerEvents(new PickupArrowListener(), instance);
		pm.registerEvents(new DamageListener(), instance);
		pm.registerEvents(new BlockListener(), instance);
		pm.registerEvents(new DropListener(), instance);
		instance.log(Level.INFO, "[SkyBridge] Listeners registered successfully!");
	}
}
