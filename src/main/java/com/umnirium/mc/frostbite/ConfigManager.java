package com.umnirium.mc.frostbite;

public class ConfigManager {
    Frostbite plugin = new Frostbite();

    public void reloadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }

    // Enable or disable message on Player join
    public boolean isMessageOnJoinEnabled() {
        return !plugin.getConfig().getBoolean("disable_join_message");
    }
}
