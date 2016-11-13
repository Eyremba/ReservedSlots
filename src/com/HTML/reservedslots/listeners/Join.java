package com.HTML.reservedslots.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.HTML.reservedslots.RS;

public class Join implements Listener {

	private RS main;

	public Join(RS pl) {
		main = pl;
	}

	@EventHandler
	public void onJoin(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		int maxPlayers = main.getConfig().getInt("MaxPlayers");
		if (main.configFile.getBoolean("PluginActive")) {
			if (Bukkit.getOnlinePlayers().size() == maxPlayers) {
				if (!(player.hasPermission("reservedslot.bypass"))) {
					player.kickPlayer(ChatColor.translateAlternateColorCodes('&', main.configFile.getString("FullServerKickMessage")));
				}
			}
		}
	}

}
