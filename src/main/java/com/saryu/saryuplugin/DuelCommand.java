package com.saryu.saryuplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DuelCommand implements CommandExecutor {
    private final DuelManager duelManager;

    public DuelCommand(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            player.sendMessage("§eUsage: /duel <player> | accept | cancel");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "accept":
                duelManager.acceptInvite(player);
                break;
            case "cancel":
                duelManager.cancelInvite(player);
                break;
            default:
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage("§cPlayer not found.");
                } else {
                    duelManager.sendInvite(player, target);
                }
                break;
        }
        return true;
    }
}

