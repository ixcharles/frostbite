package com.umnirium.mc.frostbite;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class CommandHandler {
    private final ConfigManager config;

    public CommandHandler(ConfigManager config) {
        this.config = config;
    }

    public void register(Commands commands, JavaPlugin plugin) {
        commands.register(
                Commands.literal("frostbite")
                        .requires(source -> source.getSender().hasPermission("frostbite.command"))
                        .then(
                                Commands.literal("reload")
                                        .requires(source -> source.getSender().hasPermission("frostbite.command.reload"))
                                        .executes(ctx -> {
                                                    try {
                                                        config.reloadConfig();
                                                    } catch (IOException e) {
                                                        ctx.getSource().getSender().sendRichMessage(config.getMessage("reload-error"));
                                                        throw new RuntimeException(e);
                                                    }
                                                    ctx.getSource().getSender().sendRichMessage(config.getMessage("reload-success"));

                                                    return Command.SINGLE_SUCCESS;
                                                }
                                        )
                        )
                        .then(
                              Commands.literal("support")
                                        .requires(source -> source.getSender().hasPermission("frostbite.command.support"))
                                        .executes(ctx -> {
                                              ctx.getSource().getSender().sendRichMessage("<aqua>[Frostbite]</aqua> <white>Thank you for using my plugin!</white>");
                                              ctx.getSource().getSender().sendRichMessage("<aqua>[Frostbite]</aqua> <white>Consider supporting here:</white> <yellow><click:open_url:'https://ko-fi.com/H2H61DN2C9'>https://ko-fi.com/H2H61DN2C9</click></yellow>");

                                              return Command.SINGLE_SUCCESS;
                                        })
                        )
                        .then(
                                Commands.literal("version")
                                        .requires(source -> source.getSender().hasPermission("frostbite.command.version"))
                                        .executes(ctx -> {
                                            ctx.getSource().getSender().sendRichMessage("<aqua>[Frostbite]</aqua> <white>Version 1.0.0</white>");

                                            return Command.SINGLE_SUCCESS;
                                        })
                        )
                        .then(
                                Commands.literal("help")
                                        .requires(source -> source.getSender().hasPermission("frostbite.command.help"))
                                        .executes(ctx -> {
                                            ctx.getSource().getSender().sendRichMessage("""
                                                    <aqua>[Frostbite]</aqua> <white>Realistic Cold Exposure in Minecraft</white>\
                                                    
                                                    <aqua>/frostbite help :</aqua> <white>Get Frostbite commands</white>\
                                                    
                                                    <aqua>/frostbite support :</aqua> <white>Support me with a coffee!</white>\
                                                    
                                                    <aqua>/frostbite reload :</aqua> <white>Reload config and messages</white>\
                                                    
                                                    <aqua>/frostbite version :</aqua> <white>Get plugin version</white>""");

                                            return Command.SINGLE_SUCCESS;
                                        })
                        )
                        .build(),
                "Frostbite commands",
                List.of("fb", "frost")
        );
    }
}
