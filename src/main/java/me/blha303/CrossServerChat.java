package me.blha303;

import java.io.IOException;

import com.dwarfscraft.config.BungeeConfig;
import com.google.common.eventbus.Subscribe;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class CrossServerChat extends Plugin implements Listener {
	
	BungeeConfig config;

	@Override
	public void onEnable() {
		String defaultmsg = "&7<&2%s&8-&2%s&7> &f%s";
		ProxyServer.getInstance().getPluginManager().registerListener(this);
		try {
			config = new BungeeConfig(this, "%s is replaced with (in order) server name, player display name, message. Make sure you have three %s's.");
			if (config.isNew()) {
				config.set("string", defaultmsg);
			}
		} catch (IOException e) {
			System.out.println("Could not create CrossServerChat config. Maybe you're out of disk space.");
		}
	}

	@Subscribe
	public void onChat(ChatEvent e) {
		String m = e.getMessage();
		String msg = ChatColor.translateAlternateColorCodes('&', config.getString("string"));
		if (e.getSender() instanceof ProxiedPlayer) {
			ProxiedPlayer pl = (ProxiedPlayer) e.getSender();
			for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
				if (p.getServer() != pl.getServer()) {
					p.sendMessage(String.format(msg, pl.getServer().getInfo().getName(), pl.getDisplayName(), m));
				}
			}
		}
	}

}
