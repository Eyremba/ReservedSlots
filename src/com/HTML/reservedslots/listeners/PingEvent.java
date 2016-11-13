package com.HTML.reservedslots.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.HTML.reservedslots.RS;

public class PingEvent implements Listener {

	private RS main;

	public PingEvent(RS pl) {
		main = pl;
	}

	@EventHandler
	public void onPingEvent(ServerListPingEvent e) {
		int maxPlayers = main.configFile.getInt("MaxPlayers");
		int reservedSlots = main.configFile.getInt("ReservedSlots");
		if(main.configFile.getBoolean("PluginActive")) {
		e.setMaxPlayers(maxPlayers - reservedSlots);
		}
	}
	
}
