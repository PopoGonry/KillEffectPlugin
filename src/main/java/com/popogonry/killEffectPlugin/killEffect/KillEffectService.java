package com.popogonry.killEffectPlugin.killEffect;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class KillEffectService {

    KillEffectRepository killEffectRepository = new KillEffectRepository();

    public boolean createKillEffect(String name, String mysticmobName, String lore, double cooldown, KillEffectActiveType activeType) {

        // 같은 이름의 킬 이펙트가 존재할 시, 예외
        if(KillEffectRepository.killEffectSet.contains(name)) {
            return false;
        }

        KillEffectRepository.killEffectSet.add(name);
        KillEffectRepository.killEffectHashMap.put(name, new KillEffect(name, mysticmobName, lore, cooldown, activeType));

        killEffectRepository.saveKillEffectSet();
        killEffectRepository.saveKillEffect(name);

        return true;
    }

    public boolean updateKillEffect(String oldKillEffectName, KillEffect newKillEffect) {

        // 기존 이름의 킬 이펙트가 존재하지 않을 시, 예외
        if(!KillEffectRepository.killEffectSet.contains(oldKillEffectName)) {
            return false;
        }
        // 새로운 이름의 킬 이펙트가 존재할 시, 예외
        if(KillEffectRepository.killEffectSet.contains(newKillEffect.getName())) {
            return false;
        }

        removeKillEffect(oldKillEffectName);
        createKillEffect(newKillEffect.getName(), newKillEffect.getMysticmobName(), newKillEffect.getLore(), newKillEffect.getCooldown(), newKillEffect.getActiveType());

        return true;
    }

    public boolean removeKillEffect(String name) {
        // 같은 이름의 킬 이펙트가 존재하지 않을 시, 예외
        if(!KillEffectRepository.killEffectSet.contains(name)) {
            return false;
        }

        KillEffectRepository.killEffectSet.remove(name);
        KillEffectRepository.killEffectHashMap.remove(name);

        return true;
    }

    public void printKillEffectList(CommandSender sender) {
        for (String killEffectName : KillEffectRepository.killEffectSet) {
            sender.sendMessage(killEffectName);
        }
    }

    public boolean addKillEffectToUser(Player player, String killEffectName) {
        // 킬 이펙트가 존재하지 않을 시, 예외
        if(!KillEffectRepository.killEffectSet.contains(killEffectName)) {
            return false;
        }

        // 플레이어가 킬 이펙트를 가지고 있을 시,
        if(KillEffectRepository.userKillEffectHashMap.get(player.getUniqueId()).contains(killEffectName)) {
            return false;
        }

        // Player가 온라인이 아닐 시,
        if(!player.isOnline()) {
            killEffectRepository.loadUserKillEffectSet(player.getUniqueId());
        }

        Set<String> killEffectsSet = KillEffectRepository.userKillEffectHashMap.get(player.getUniqueId());
        killEffectsSet.add(killEffectName);
        KillEffectRepository.userKillEffectHashMap.put(player.getUniqueId(), killEffectsSet);

        // Player가 온라인일 시,
        if(player.isOnline()) {
            killEffectRepository.saveUserKillEffectSet(player.getUniqueId());
        }
        // Player가 온라인이 아닐 시,
        else {
            killEffectRepository.storeUserKillEffectSet(player.getUniqueId());

        }
        return true;
    }

    public boolean removeKillEffectFromUser(Player player, String killEffectName) {
        // 킬 이펙트가 존재하지 않을 시, 예외
        if(!KillEffectRepository.killEffectSet.contains(killEffectName)) {
            return false;
        }

        // 플레이어가 킬 이펙트를 가지고 있지 않을 시,
        if(!KillEffectRepository.userKillEffectHashMap.get(player.getUniqueId()).contains(killEffectName)) {
            return false;
        }

        // Player가 서버에 없을 경우,
        if(!player.isOnline()) {
            killEffectRepository.loadUserKillEffectSet(player.getUniqueId());
        }

        Set<String> killEffectsSet = KillEffectRepository.userKillEffectHashMap.get(player.getUniqueId());
        killEffectsSet.remove(killEffectName);
        KillEffectRepository.userKillEffectHashMap.put(player.getUniqueId(), killEffectsSet);

        // Player가 서버에 있을 경우,
        if(player.isOnline()) {
            killEffectRepository.saveUserKillEffectSet(player.getUniqueId());
        }
        // Player가 서버에 없을 경우,
        else {
            killEffectRepository.storeUserKillEffectSet(player.getUniqueId());

        }
        return true;
    }










    public void printUserKillEffectList(CommandSender sender) {
        for (UUID uuid : KillEffectRepository.userKillEffectHashMap.keySet()) {
            StringBuilder sb = new StringBuilder(KillEffectRepository.userKillEffectHashMap.get(uuid).stream().collect(Collectors.joining(", ")));

            sender.sendMessage(uuid.toString() + " : " + sb);
        }
    }

}
