package com.saryu.saryuplugin;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.Queue;

public class DuelManager {

    private final JavaPlugin plugin;
    private final Queue<Player> queue = new LinkedList<>();
    private boolean arenaBusy = false;

    private MultiverseCore mvCore;
    private MVWorldManager worldManager;

    public DuelManager(JavaPlugin plugin) {
        this.plugin = plugin;

        Plugin p = Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        if (p instanceof MultiverseCore) {
            this.mvCore = (MultiverseCore) p;
            this.worldManager = mvCore.getMVWorldManager();
            plugin.getLogger().info("Multiverse-Core detected");
        } else {
            this.mvCore = null;
            this.worldManager = null;
            plugin.getLogger().warning("Multiverse-Core NOT found, fallback Bukkit world only");
        }
    }

    public void joinQueue(Player player) {
        if (queue.contains(player)) {
            player.sendMessage("§4You're already in queue");
            return;
        }

        queue.add(player);
        player.sendMessage("§aEntered Queue: " + queue.size() + " /2");

        tryStartDuel();
    }

    private void tryStartDuel() {
        if (arenaBusy) return;
        if (queue.size() < 2) return;

        Player p1 = queue.poll();
        Player p2 = queue.poll();

        if (p1 == null || p2 == null) return;

        arenaBusy = true;
        startDuel(p1, p2);
    }

    private void startDuel(Player p1, Player p2) {
        String worldName = plugin.getConfig().getString("duel.world");
        World world = getOrLoadWorld(worldName);

        if (world == null) {
            p1.sendMessage("§4Arena not available");
            p2.sendMessage("§4Arena not available");
            arenaBusy = false;
            return;
        }

        Location a = getLocation("duel.posA", world);
        Location b = getLocation("duel.posB", world);

        p1.teleport(a);
        p2.teleport(b);

        p1.sendMessage("§4§oDuel starting in 3 seconds...");
        p2.sendMessage("§4§oDuel starting in 3 seconds...");

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            p1.sendMessage("§cFIGHT!");
            p2.sendMessage("§cFIGHT!");
        }, 60L);
    }

    public void endDuel(Player winner, Player loser) {
        winner.sendMessage("§a§oYou Win!!");
        loser.sendMessage("§c§oYou Lose!!");

        arenaBusy = false;
        tryStartDuel();
    }

    private World getOrLoadWorld(String worldName) {
        if (worldName == null) return null;
        
        if (worldManager != null) {
            if (!worldManager.isMVWorld(worldName)) return Bukkit.getWorld(worldName);

            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                worldManager.loadWorld(worldName);
                world = Bukkit.getWorld(worldName);
            }
            return world;
        }
        return Bukkit.getWorld(worldName);
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
