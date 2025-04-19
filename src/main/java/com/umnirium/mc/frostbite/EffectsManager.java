package com.umnirium.mc.frostbite;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class EffectsManager implements Listener {
    ConfigManager config = new ConfigManager();

    int freezeTicks = 0;

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FREEZE) {
                player.setWalkSpeed(0.0f);
                event.setCancelled(true);
            }
        }
    }

    public void startEffectTask(Frostbite plugin, HashMap<String, BukkitRunnable> tasks, Player player) {
        long delay = config.getEffectsDelay();
        long interval = config.getEffectsInterval();

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                player.setFreezeTicks(freezeTicks);
                freezeTicks += 20;
            }
        };

        task.runTaskTimer(plugin, delay, interval);
        tasks.put(player.getName(), task);
    }

    public void stopEffectTask(HashMap<String, BukkitRunnable> tasks, Player player) {
        BukkitRunnable task = tasks.remove(player.getName());

        if (task != null) {
            task.cancel();
        }
    }
}
