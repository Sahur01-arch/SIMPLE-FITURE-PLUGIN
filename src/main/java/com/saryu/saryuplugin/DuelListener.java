package com.saryu.saryuplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DuelListener implements Listener {

    private final DuelManager duelManager;

    public DuelListener(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Reset arena whenever a death occurs 
        duelManager.endDuel();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Ensure arena is not locked if a player leaves during a fight
        duelManager.endDuel();
    }
}

