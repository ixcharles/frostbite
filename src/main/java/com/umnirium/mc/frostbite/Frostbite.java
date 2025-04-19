package com.umnirium.mc.frostbite;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class Frostbite extends JavaPlugin {
    MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public void onEnable() {

        getComponentLogger().info(mm.deserialize("<aqua>[Frostbite]</aqua> <white>Plugin successfully enabled</white>"));
        getComponentLogger().info(mm.deserialize("<aqua>[Frostbite]</aqua> <white>Consider supporting here:</white> <yellow><click:open_url:'https://ko-fi.com/H2H61DN2C9'>https://ko-fi.com/H2H61DN2C9</click></yellow>"));
    }

    @Override
    public void onDisable() {
        getComponentLogger().info(mm.deserialize("<aqua>[Frostbite]</aqua> <white>Plugin successfully disabled</white>"));
    }
}
