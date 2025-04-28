package com.umnirium.mc.frostbite;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class ColdBiomeListener implements Listener {
    private final Frostbite plugin;
    private final EffectsManager effectsManager;
    private final ConfigManager config;

    public final HashMap<UUID, BukkitRunnable> activeTasks = new HashMap<>();
    private final HashSet<UUID> isMessageSentToPlayer = new HashSet<>();

    public ColdBiomeListener(Frostbite plugin, ConfigManager config, EffectsManager effectsManager) {
        this.plugin = plugin;
        this.effectsManager = effectsManager;
        this.config = config;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getChunk() != event.getTo().getChunk()) {
            String chunk = event.getTo().getBlock().getBiome().getKey().toString();

            if (config.getBiomes().contains(chunk)) {
                if (!activeTasks.containsKey(player.getUniqueId())) {
                    effectsManager.startEffectTask(plugin, activeTasks, player);
                }

                if (config.areMessagesEnabled() && !isMessageSentToPlayer.contains(player.getUniqueId())) {
                    isMessageSentToPlayer.add(player.getUniqueId());
                    player.sendRichMessage(config.getMessage("entering-cold-biome"));
                }
            }

            else {
                effectsManager.stopEffectTask(activeTasks, player);

                if (config.areMessagesEnabled() && isMessageSentToPlayer.contains(player.getUniqueId())) {
                    isMessageSentToPlayer.remove(player.getUniqueId());
                    player.sendRichMessage(config.getMessage("leaving-cold-biome"));
                }
            }
        }
    }
}
