package com.saryu.saryuplugin;

import org.mvplugins.multiverse.core.MultiverseCoreApi;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        // Cek Multiverse-Core API v5
        if (Bukkit.getServicesManager().getRegistration(MultiverseCoreApi.class) == null) {
            getLogger().severe("Multiverse-Core v5 tidak ditemukan! Duel tidak akan berjalan.");
        } else {
            getLogger().info("Multiverse-Core v5 API hooked.");
        }

        // Duel system
        DuelManager duelManager = new DuelManager(this);
        getCommand("duel").setExecutor(new DuelCommand(duelManager));
        getServer().getPluginManager().registerEvents(new DuelListener(duelManager), this);

        // Command lain
        BaseCommand baseCommand = new BaseCommand(this);
        getCommand("pvp").setExecutor(baseCommand);
        getCommand("gacha").setExecutor(baseCommand);

        // Listener
        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("SimpleFiture Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleFiture Disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(
                Component.text("Halo Pemain!", NamedTextColor.GREEN)
        );
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {

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
