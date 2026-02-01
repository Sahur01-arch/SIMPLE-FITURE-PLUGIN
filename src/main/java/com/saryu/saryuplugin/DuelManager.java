package com.saryu.saryuplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.Queue;

public class DuelManager {

    private final JavaPlugin plugin;
    private final Queue<Player> queue = new LinkedList<>();
    private boolean arenaBusy = false;

    public DuelManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void joinQueue(Player player) {
        if (queue.contains(player)) {
            player.sendMessage("§cYou're already in queue");
            return;
        }

        queue.add(player);
        player.sendMessage("§aQueue: " + queue.size() + "/2");
        tryStartDuel();
    }

    private void tryStartDuel() {
        if (arenaBusy || queue.size() < 2) return;

        Player p1 = queue.poll();
        Player p2 = queue.poll();

        if (p1 != null && p2 != null) {
            arenaBusy = true;
            startDuel(p1, p2);
        }
    }

    private void startDuel(Player p1, Player p2) {
        String worldName = plugin.getConfig().getString("duel.world", "world");
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            p1.sendMessage("§cArena world not loaded");
            p2.sendMessage("§cArena world not loaded");
            arenaBusy = false;
            return;
        }

        Location posA = getLocation("duel.posA", world);
        Location posB = getLocation("duel.posB", world);

        p1.teleport(posA);
        p2.teleport(posB);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            p1.sendMessage("§c§lFIGHT!");
            p2.sendMessage("§c§lFIGHT!");
        }, 60L);
    }

    public void endDuel(Player p1, Player p2) {
        arenaBusy = false;
    }

    private Location getLocation(String path, World world) {
        FileConfiguration c = plugin.getConfig();

        return new Location(
                world,
                c.getDouble(path + ".x"),
                c.getDouble(path + ".y"),
                c.getDouble(path + ".z"),
                (float) c.getDouble(path + ".yaw"),
                (float) c.getDouble(path + ".pitch")
        );
    }
}
