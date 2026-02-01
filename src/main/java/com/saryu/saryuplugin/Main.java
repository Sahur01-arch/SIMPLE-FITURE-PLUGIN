package com.saryu.saryuplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);
        
        DuelManager duelManager = new DuelManager(this);

        if (getCommand("duel") != null) {
            getCommand("duel").setExecutor(new DuelCommand(duelManager));
        }
        
        getServer().getPluginManager().registerEvents(new DuelListener(duelManager), this);

        if (getCommand("test") != null) getCommand("test").setExecutor(this);
        
        if (getCommand("pvp") != null) {
            getCommand("pvp").setExecutor(new BaseCommand(this));
        }
        
        if (getCommand("gacha") != null) {
            getCommand("gacha").setExecutor(new BaseCommand(this));
        }
        
        if (getCommand("smfp") != null) getCommand("smfp").setExecutor(this);

        getLogger().info("§aPlugin Saryu Berhasil di-load!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(
            Component.text("Halo Pemain!", NamedTextColor.GREEN)
        );
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin nonaktif.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("test")) {
            if (sender instanceof Player player) {
                player.sendMessage(
                    Component.text("Yo.", NamedTextColor.GREEN)
                             .append(Component.text("!Hahahah", NamedTextColor.RED))
                );
            } else {
                sender.sendMessage(Component.text("Nandayo?", NamedTextColor.BLUE));
            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("smfp")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("group.maindev")) {
                    sender.sendMessage(Component.text("Kamu tidak punya izin!", NamedTextColor.RED));
                    return true;
                }
                reloadConfig();
                sender.sendMessage(Component.text("Config Reloaded", NamedTextColor.GREEN));
                return true;
            }
            sender.sendMessage(Component.text("Gunakan: /smfp reload", NamedTextColor.YELLOW));
            return true;
        }
        return false;
    }
}
