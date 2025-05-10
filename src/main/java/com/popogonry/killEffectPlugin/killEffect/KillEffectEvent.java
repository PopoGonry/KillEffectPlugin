package com.popogonry.killEffectPlugin.killEffect;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class KillEffectEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        KillEffectRepository killEffectRepository = new KillEffectRepository();

        killEffectRepository.loadUserKillEffectSet(player.getUniqueId());
        killEffectRepository.loadUserKillEffect(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        KillEffectRepository killEffectRepository = new KillEffectRepository();

        killEffectRepository.storeUserKillEffectSet(player.getUniqueId());
        killEffectRepository.storeUserKillEffect(player.getUniqueId());
    }

}
