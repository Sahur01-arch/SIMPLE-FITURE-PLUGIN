package com.saryu.saryuplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {

    private final DuelManager duelManager;

    public DuelCommand(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only player!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§e/duel join");
            return true;
        }

        if (args[0].equalsIgnoreCase("join")) {
            duelManager.joinQueue(player);
            return true;
        }

        return true;
    }
}
