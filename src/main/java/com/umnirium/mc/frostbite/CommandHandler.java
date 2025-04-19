package com.umnirium.mc.frostbite;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class CommandHandler {
    ConfigManager config = new ConfigManager();

    private final SuggestionProvider<CommandSourceStack> frostbiteSuggestions =
            ((commandContext, suggestionsBuilder) -> {
                    List<String> suggestions = List.of("reload");

                    suggestions.forEach(suggestionsBuilder::suggest);

                    return suggestionsBuilder.buildFuture();
                }
            );

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
                                    ).build(),
                "Frostbite commands",
                List.of("fb", "frost")
        );
    }
}
