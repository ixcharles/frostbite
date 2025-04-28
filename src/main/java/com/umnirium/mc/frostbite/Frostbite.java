package com.umnirium.mc.frostbite;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class Frostbite extends JavaPlugin {
    MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ConfigManager config = new ConfigManager();
        EffectsManager effectsManager = new EffectsManager();

        config.createMessagesFile();

        Bukkit.getPluginManager().registerEvents(new PlayerConnectListener(config), this);
        Bukkit.getPluginManager().registerEvents(new ColdBiomeListener(this, config, effectsManager), this);
        Bukkit.getPluginManager().registerEvents(new EffectsManager(), this);

        LifecycleEventManager<@NotNull Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            new CommandHandler(config).register(commands, this);
        });

        getComponentLogger().info(mm.deserialize("<aqua>[Frostbite]</aqua> <white>Plugin successfully enabled</white>"));
        getComponentLogger().info(mm.deserialize("<aqua>[Frostbite]</aqua> <white>Consider supporting here:</white> <yellow><click:open_url:'https://ko-fi.com/H2H61DN2C9'>https://ko-fi.com/H2H61DN2C9</click></yellow>"));
    }

    @Override
    public void onDisable() {
        getComponentLogger().info(mm.deserialize("<aqua>[Frostbite]</aqua> <white>Plugin successfully disabled</white>"));
    }
}
