package com.umnirium.mc.frostbite;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {
    Frostbite plugin = new Frostbite();

    private File messagesFile;
    private FileConfiguration messagesConfig;

    public void reloadConfig() throws IOException {
            plugin.saveDefaultConfig();
            plugin.reloadConfig();

            reloadMessages();
    }

    public void createMessagesFile() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void reloadMessages() {
        createMessagesFile();
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
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

    public long getEffectsDelay() {
        return plugin.getConfig().getInt("effects.delay") * 20L;
    }

    public long getEffectsInterval() {
        return plugin.getConfig().getInt("effects.interval") * 20L;
    }

    public boolean isDamageEnabled() {
        return plugin.getConfig().getBoolean("effects.damage.enabled");
    }

    public double getDamageValue() {
        return plugin.getConfig().getDouble("effects.damage.value");
    }
}
