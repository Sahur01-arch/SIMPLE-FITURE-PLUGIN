package com.saryu.saryuplugin;

import org.mvplugins.multiverse.core.MultiverseCoreApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DuelManager {

    private final JavaPlugin plugin;
    private final MultiverseCoreApi mvApi;
    private final Map<UUID, UUID> pendingInvites = new HashMap<>();
    private boolean arenaBusy = false;

    public DuelManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.mvApi = Bukkit.getServicesManager()
                .getRegistration(MultiverseCoreApi.class)
                .getProvider();
    }

    public void sendInvite(Player sender, Player receiver) {
        if (sender.equals(receiver)) {
            sender.sendMessage("§cYou cannot duel yourself!");
            return;
        }

        pendingInvites.put(receiver.getUniqueId(), sender.getUniqueId());
        sender.sendMessage("§aDuel request sent to " + receiver.getName());
        receiver.sendMessage("§e" + sender.getName() + " §ahas challenged you to a duel!");
        receiver.sendMessage("§7Type §b/duel accept §7to start.");
    }

    public void acceptInvite(Player receiver) {
        UUID senderUUID = pendingInvites.remove(receiver.getUniqueId());
        if (senderUUID == null) {
            receiver.sendMessage("§cYou have no pending duel requests.");
            return;
        }

        Player sender = Bukkit.getPlayer(senderUUID);
        if (sender == null || !sender.isOnline()) {
            receiver.sendMessage("§cThe challenger is no longer online.");
            return;
        }

        if (arenaBusy) {
            receiver.sendMessage("§cArena is busy. Please wait.");
            return;
        }

        startDuel(sender, receiver);
    }

    public void cancelInvite(Player sender) {
        pendingInvites.values().removeIf(uuid -> uuid.equals(sender.getUniqueId()));
        sender.sendMessage("§eAll your outgoing duel requests have been canceled.");
    }

    // ===================== DUEL CORE =====================

    private void startDuel(Player p1, Player p2) {

        String worldName = plugin.getConfig().getString("duel.world", "world");

        mvApi.getWorldManager()
                .getWorld(worldName)
                .onEmpty(() -> {
                    p1.sendMessage("§cWorld duel not found!");
                    p2.sendMessage("§cWorld duel not found!");
                })
                .peek(mvWorld -> {

                    World world = Bukkit.getWorld(worldName);
                    if (world == null) {
                        p1.sendMessage("§cWorld not loaded!");
                        p2.sendMessage("§cWorld not loaded!");
                        return;
                    }

                    arenaBusy = true;

                    p1.teleport(getLocation("duel.posA", world));
                    p2.teleport(getLocation("duel.posB", world));

                    p1.sendMessage("§6§lDUEL STARTED!");
                    p2.sendMessage("§6§lDUEL STARTED!");
                });
    }

    public void endDuel() {
        this.arenaBusy = false;
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
