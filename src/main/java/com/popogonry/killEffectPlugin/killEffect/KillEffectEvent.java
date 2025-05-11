package com.popogonry.killEffectPlugin.killEffect;

import com.popogonry.killEffectPlugin.CooldownManager;
import com.popogonry.killEffectPlugin.KillEffectPlugin;
import com.popogonry.killEffectPlugin.Reference;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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

    @EventHandler
    public void onUseKillEffectBook(PlayerInteractEvent event) {
        if(!event.getAction().isRightClick()) return;

        if(event.getPlayer().getItemInHand() == null) return;

        ItemStack itemStack = new ItemStack(event.getPlayer().getItemInHand());

        if(itemStack.getType() == Material.AIR) return;

        if(itemStack.getType() != Material.ENCHANTED_BOOK) return;

        List<String> lore = itemStack.getItemMeta().getLore();
        if(lore == null) return;

        Boolean isRightKillEffectBook = false;

        for (String s : lore) {
            if(s.contains("KillEffect")) {
                isRightKillEffectBook = true;
            }
        }

        if(!isRightKillEffectBook) return;

        Player player = event.getPlayer();
        String killEffectName = itemStack.getItemMeta().getDisplayName().replaceAll(ChatColor.GOLD.toString(), "");

        KillEffectService killEffectService = new KillEffectService();
        if(killEffectService.addKillEffectToUser(player, killEffectName)) {
            player.sendMessage(Reference.prefix_normal + killEffectName + " 킬이펙트가 추가 되었습니다.");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            event.getPlayer().setItemInHand(null);
        }
        else {
            player.sendMessage(Reference.prefix_error + "이미 보유 중인 킬이펙트입니다.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onDeathEntity(EntityDeathEvent event) {
        if(event.getEntity().getKiller() == null) return;
        if(!(event.getEntity().getKiller() instanceof Player)) return;

        Player killer = event.getEntity().getKiller();

        if(!KillEffectRepository.userKillEffectHashMap.containsKey(killer.getUniqueId())) return;

        String killEffectName = KillEffectRepository.userKillEffectHashMap.get(killer.getUniqueId());

        if(!KillEffectRepository.killEffectSet.contains(killEffectName)) return;

        KillEffect killEffect = KillEffectRepository.killEffectHashMap.get(killEffectName);



        if(killEffect.getActiveType() == KillEffectActiveType.ALL
                || (killEffect.getActiveType() == KillEffectActiveType.PLAYER && event.getEntity() instanceof Player)
                || killEffect.getActiveType() == KillEffectActiveType.MOB && (event.getEntity() instanceof Entity && !(event.getEntity() instanceof Player))) {

            CooldownManager cooldownManager = new CooldownManager();

            if (cooldownManager.isOnCooldown(killer, (long) (killEffect.getCooldown() * 1000))) {
//                killer.sendMessage(String.valueOf(cooldownManager.getRemainingTime(killer, (long) (killEffect.getCooldown() * 1000))));
                return;
            }

            CooldownManager.cooldowns.put(killer.getUniqueId(), System.currentTimeMillis());

            MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(killEffect.getMysticmobName()).orElse(null);
            Location spawnLocation = event.getEntity().getLocation();
            if(mob != null){
                // spawns mob
                ActiveMob activeMob = mob.spawn(BukkitAdapter.adapt(spawnLocation),1);
            }
        }
    }
}
