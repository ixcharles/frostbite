package com.umnirium.mc.frostbite;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EffectsManager implements Listener {
    ConfigManager config = new ConfigManager();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FREEZE) {
                event.setCancelled(true);
            }
        }
    }

    public void startEffectTask(Frostbite plugin, HashMap<String, BukkitRunnable> tasks, Player player) {
        long delay = config.getEffectsDelay();
        long interval = config.getEffectsInterval();

        BukkitRunnable task = new BukkitRunnable() {
            int freezeTicks = 0;
            int effectCount = 0;

            @Override
            public void run() {
                if (new ColdProtection().isColdProtected(player)) {
                    player.sendRichMessage("I'm protected");
                    freezeTicks = 0;
                    effectCount = 0;
                }

                else {
                    player.sendRichMessage("freezing");
                    player.setFreezeTicks(freezeTicks);
                    freezeTicks = freezeTicks >= 500 ? 500 : freezeTicks + 20;

                    if (config.isDamageEnabled()) {
                        player.damage(config.getDamageValue());
                    }

                    if (config.areEffectsEnabled()) {
                        List<EffectData> effects = config.getEffectsData();

                        if (effectCount < effects.size()) {
                            String effectName = effects.get(effectCount).name();
                            int effectTime = effects.get(effectCount).time();
                            int effectAmplifier = effects.get(effectCount).amplifier();

                            PotionEffectType effectType = Registry.EFFECT.get(NamespacedKey.minecraft(effectName));

                            player.addPotionEffect(new PotionEffect(Objects.requireNonNull(effectType), effectTime, effectAmplifier));

                            effectCount++;
                        }
                        else {
                            effectCount = 0;
                        }
                    }
                }
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
