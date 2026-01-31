package com.saryu.saryuplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Random;

public class BaseCommand implements CommandExecutor {

    private final Main plugin;
    private final Random random = new Random();

    public BaseCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text(
                    "Hanya Player yang bisa menggunakan command ini!",
                    NamedTextColor.RED
            ));
            return true;
        }

        if (command.getName().equalsIgnoreCase("pvp")) {
            String worldName = plugin.getConfig().getString("pvp-world", "pvp");

            player.sendMessage(Component.text(
                    "Teleporting ke " + worldName + "...",
                    NamedTextColor.GREEN
            ));

            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "mv tp " + player.getName() + " " + worldName
            );
            return true;
        }

        if (command.getName().equalsIgnoreCase("gacha")) {

            int randomNum = random.nextInt(20) + 1; 
            player.sendMessage("§7Gacha roll: §e" + randomNum);

            if (randomNum > 15) {
                player.sendMessage("§4You so lucky!");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "eco give " + player.getName() + " 100");

            } else if (randomNum > 10) {
                player.sendMessage("§eWow!");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "eco give " + player.getName() + " 15");

            } else if (randomNum > 5) {
                player.sendMessage("§aKamu beruntung");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "eco give " + player.getName() + " 5");

            } else {
                player.sendMessage("§cYou're unlucky");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "eco give " + player.getName() + " 1");
            }

            return true;
        }

        return false;
    }
}
