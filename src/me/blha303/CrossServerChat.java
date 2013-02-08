package me.blha303;

import com.google.common.eventbus.Subscribe;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class CrossServerChat extends Plugin implements Listener {

	@Override
	public void onEnable() {
		ProxyServer.getInstance().getPluginManager().registerListener(this);
	}

	@Subscribe
	public void onChat(ChatEvent e) {
		String m = e.getMessage();
		String msg = ChatColor.translateAlternateColorCodes('&', "&7<&2%s&8-&2%s&7> &f%s");
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
