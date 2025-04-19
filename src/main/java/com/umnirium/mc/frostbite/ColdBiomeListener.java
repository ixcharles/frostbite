package com.umnirium.mc.frostbite;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ColdBiomeListener implements Listener {
    boolean msg = false;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getChunk() != event.getTo().getChunk()) {
            String chunk = event.getTo().getBlock().getBiome().getKey().toString();

            if (new ConfigManager().getBiomes().contains(chunk)) {
                if (!msg) {
                    msg = true;
                    player.sendRichMessage("Cold");
                }
            }

            else {
                if (msg) {
                    msg = false;
                    player.sendRichMessage("Warm");
                }
            }
        }
    }
}
