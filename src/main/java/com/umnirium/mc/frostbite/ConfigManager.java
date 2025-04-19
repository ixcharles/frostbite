package com.umnirium.mc.frostbite;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    static final Frostbite plugin = new Frostbite();

    private FileConfiguration messagesConfig = createMessagesFile();

    public void reloadConfig() throws IOException {
            plugin.saveDefaultConfig();
            plugin.reloadConfig();

            reloadMessages();
    }

    public @NotNull YamlConfiguration createMessagesFile() {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        return YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reloadMessages() {
        messagesConfig = createMessagesFile();
    }

    // Enable or disable message on Player join
    public boolean isMessageOnJoinEnabled() {
        return !plugin.getConfig().getBoolean("disable-join-message");
    }

    public String getMessage(String path) {
        return messagesConfig.getString(path);
    }

    public List<String> getBiomes() {
        return plugin.getConfig().getStringList("biomes");
    }

    public boolean areMessagesEnabled() {
        return plugin.getConfig().getBoolean("messages");
    }

    public long getEffectsDelay() {
        return plugin.getConfig().getInt("effects.delay") * 20L;
    }

    public long getEffectsInterval() {
        return plugin.getConfig().getInt("effects.interval") * 20L;
    }

    public List<EffectData> getEffectsData() {
        @NotNull List<Map<?, ?>> effectsInConfig = plugin.getConfig().getMapList("effects.effects");

        List<EffectData> effects = new ArrayList<>();

        for (Map<?, ?> effect : effectsInConfig) {
            for (Object effectName : effect.keySet()) {
                Map<?, ?> effectProps = (Map<?, ?>) effect.get(effectName.toString());

                for (Object effectProp : effectProps.keySet()) {
                    long time = effectProp.toString().equals("time") ? (int) effectProps.get(effectProp.toString()) * 20L : 0L;
                    int amplifier = effectProp.toString().equals("amplifier") ? (int) effectProps.get(effectProp.toString()) : 0;

                    effects.add(new EffectData(effectName.toString(), time, amplifier));
                }
            }
        }
        return effects;
    }

    public boolean isDamageEnabled() {
        return plugin.getConfig().getBoolean("effects.damage.enabled");
    }

    public double getDamageValue() {
        return plugin.getConfig().getDouble("effects.damage.value");
    }
}
