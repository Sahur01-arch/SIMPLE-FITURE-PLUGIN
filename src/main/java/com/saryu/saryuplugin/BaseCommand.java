package com.saryu.saryuplugin;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

public class BaseCommand implements CommandExecutor {

    private final Main plugin;

    public BaseCommand(Main plugin) {
        this.plugin = plugin; 
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) { 
            String worldName = plugin.getConfig().getString("pvp-world", "pvp");

            player.sendMessage(Component.text("Teleporting to " + worldName + "...", NamedTextColor.GREEN));
            
            plugin.getServer().dispatchCommand(
                plugin.getServer().getConsoleSender(), 
                "mv tp " + player.getName() + " " + worldName
            );
        } else {
            sender.sendMessage(Component.text("Hanya Player yang bisa menggunakan ini!!!", NamedTextColor.RED));
        }
        return true;
    }
}

