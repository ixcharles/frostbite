package com.umnirium.mc.frostbite;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectListener implements Listener {
    ConfigManager config = new ConfigManager();

    @EventHandler
    public void onPlayerConnect (PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (config.isMessageOnJoinEnabled() && !player.hasPermission("frostbite.disablejoinmessage")) {
            player.sendRichMessage("<aqua>[Frostbite]</aqua> <white>Thank you for using my plugin!</white>");
            player.sendRichMessage("<aqua>[Frostbite]</aqua> <white>Consider supporting here:</white> <yellow><click:open_url:'https://ko-fi.com/H2H61DN2C9'>https://ko-fi.com/H2H61DN2C9</click></yellow>");
        }
    }
}
