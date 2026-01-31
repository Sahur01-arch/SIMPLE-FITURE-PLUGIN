package com.saryu.saryuplugin;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import net.milkbowl.vault.economy.EconomyResponse;

int min = 1
int max = 20

int randomNum = (int)(Math.random() * ((max - min) + 1)) + min;

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
        
        if (command.getName().equalsIgnoreCase("gacha")) {
          if (randomNum > 20) {
            sender.sendMessage("§4You're unlucky");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName() + " 1")
          }
          else if (randomNum > 15) {
            sender.sendMessage("§eKamu beruntung");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName() + " 5");
          }
          else if (randomNum > 10) {
            sender.sendMessage("§4Wow");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName() + " 15")
          }
          else if (randomNum > 5) {
            sender.sendMessage("§4You so lucky!")
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName() + " 100")
          }
        }
        }
    }
}

