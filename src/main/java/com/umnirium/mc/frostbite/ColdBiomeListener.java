package com.umnirium.mc.frostbite;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ColdBiomeListener implements Listener {
    Frostbite plugin;
    EffectsManager effectsManager = new EffectsManager();

    boolean isMessageSent = false;
    public final HashMap<String, BukkitRunnable> activeTasks = new HashMap<>();

    public ColdBiomeListener(Frostbite plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getChunk() != event.getTo().getChunk()) {
            String chunk = event.getTo().getBlock().getBiome().getKey().toString();

            if (new ConfigManager().getBiomes().contains(chunk)) {
                if (!activeTasks.containsKey(player.getName())) {
                    effectsManager.startEffectTask(plugin, activeTasks, player);
                }

                if (!isMessageSent) {
                    isMessageSent = true;
                    player.sendRichMessage("Cold");
                }
            }

            else {
                effectsManager.stopEffectTask(activeTasks, player);

                if (isMessageSent) {
                    isMessageSent = false;
                    player.sendRichMessage("Warm");
                }
            }
        }
    }
}
