package com.popogonry.killEffectPlugin.killEffect;

import com.popogonry.killEffectPlugin.GUI;
import com.popogonry.killEffectPlugin.PluginRepository;
import com.popogonry.killEffectPlugin.Reference;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
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
        // 이전의 킬이펙트 이름과 같지 않을 때,
        if(!oldKillEffectName.equals(newKillEffect.getName())) {
            return false;
        }

        removeKillEffect(oldKillEffectName);
        createKillEffect(newKillEffect.getName(), newKillEffect.getMysticmobName(), newKillEffect.getLore(), newKillEffect.getCooldown(), newKillEffect.getActiveType());

        killEffectRepository.saveKillEffectSet();
        killEffectRepository.saveKillEffect(newKillEffect.getName());
        return true;
    }

    public boolean removeKillEffect(String name) {
        // 같은 이름의 킬 이펙트가 존재하지 않을 시, 예외
        if(!KillEffectRepository.killEffectSet.contains(name)) {
            return false;
        }

        KillEffectRepository.killEffectSet.remove(name);
        KillEffectRepository.killEffectHashMap.remove(name);

        killEffectRepository.saveKillEffectSet();
        killEffectRepository.saveKillEffect(name);
        return true;
    }

    public boolean addKillEffectToUser(Player player, String killEffectName) {
        // 킬 이펙트가 존재하지 않을 시, 예외
        if(!KillEffectRepository.killEffectSet.contains(killEffectName)) {
            return false;
        }

        // Player가 온라인이 아닐 시,
        if(!player.isOnline()) {
            killEffectRepository.loadUserKillEffectSet(player.getUniqueId());
        }

        // 플레이어가 킬 이펙트를 가지고 있을 시,
        if(KillEffectRepository.userKillEffectSetHashMap.get(player.getUniqueId()).contains(killEffectName)) {
            return false;
        }

        Set<String> killEffectsSet = KillEffectRepository.userKillEffectSetHashMap.get(player.getUniqueId());
        killEffectsSet.add(killEffectName);
        KillEffectRepository.userKillEffectSetHashMap.put(player.getUniqueId(), killEffectsSet);

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
//        // 킬 이펙트가 존재하지 않을 시, 예외
//        if(!KillEffectRepository.killEffectSet.contains(killEffectName)) {
//            return false;
//        }

        // Player가 온라인이 아닐 시,
        if(!player.isOnline()) {
            killEffectRepository.loadUserKillEffectSet(player.getUniqueId());
        }

        // 플레이어가 킬 이펙트를 가지고 있지 않을 시,
        if(!KillEffectRepository.userKillEffectSetHashMap.get(player.getUniqueId()).contains(killEffectName)) {
            return false;
        }

        Set<String> killEffectsSet = KillEffectRepository.userKillEffectSetHashMap.get(player.getUniqueId());
        killEffectsSet.remove(killEffectName);
        KillEffectRepository.userKillEffectSetHashMap.put(player.getUniqueId(), killEffectsSet);

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

    public boolean setUserKillEffect(Player player, String killEffectName) {
        // 킬 이펙트가 존재하지 않을 시, 예외
        if(!KillEffectRepository.killEffectSet.contains(killEffectName)) {
            return false;
        }

        // Player가 온라인이 아닐 시,
        if(!player.isOnline()) {
            killEffectRepository.loadUserKillEffect(player.getUniqueId());
        }

        // 플레이어가 이미 같은 킬 이펙트를 장착 중일 시,
        if(KillEffectRepository.userKillEffectHashMap.getOrDefault(player.getUniqueId(), "").equals(killEffectName)) {
            return false;
        }

        // 플레이어가 킬 이펙트를 가지고 있지 않을 시,
        if(!KillEffectRepository.userKillEffectSetHashMap.get(player.getUniqueId()).contains(killEffectName)) {
            return false;
        }

        KillEffectRepository.userKillEffectHashMap.put(player.getUniqueId(), killEffectName);

        // Player가 온라인일 시,
        if(player.isOnline()) {
            killEffectRepository.saveUserKillEffect(player.getUniqueId());
        }
        // Player가 온라인이 아닐 시,
        else {
            killEffectRepository.storeUserKillEffect(player.getUniqueId());

        }
        return true;

    }

    public boolean removeUserKillEffect(Player player) {
        // Player가 온라인이 아닐 시,
        if(!player.isOnline()) {
            killEffectRepository.loadUserKillEffect(player.getUniqueId());
        }

        KillEffectRepository.userKillEffectHashMap.remove(player.getUniqueId());

        // Player가 온라인일 시,
        if(player.isOnline()) {
            killEffectRepository.saveUserKillEffect(player.getUniqueId());
        }
        // Player가 온라인이 아닐 시,
        else {
            killEffectRepository.storeUserKillEffect(player.getUniqueId());

        }
        return true;
    }

    public void printKillEffectList(CommandSender sender) {
        for (String killEffectName : KillEffectRepository.killEffectSet) {
            KillEffect killEffect = KillEffectRepository.killEffectHashMap.get(killEffectName);
            sender.sendMessage("- 이름: " + killEffect.getName() + "/ 미스틱몹: " + killEffect.getMysticmobName() + "/ 설명: " + killEffect.getLore() + "/ 쿨타임: " + killEffect.getCooldown() + "/ 발동 타입: " + killEffect.getActiveType());
        }
    }
    public void printUserKillEffectList(CommandSender sender) {
        for (UUID uuid : KillEffectRepository.userKillEffectSetHashMap.keySet()) {
            StringBuilder sb = new StringBuilder(KillEffectRepository.userKillEffectSetHashMap.get(uuid).stream().collect(Collectors.joining(", ")));

            sender.sendMessage("Set / " + uuid.toString() + " : " + sb);
        }
        for (UUID uuid : KillEffectRepository.userKillEffectHashMap.keySet()) {
            sender.sendMessage("KE / " + uuid.toString() + " : " + KillEffectRepository.userKillEffectHashMap.get(uuid));
        }
    }

    public ItemStack getKillEffectBook(String killEffectName) {
        // 킬 이펙트가 존재하지 않을 시, 예외
        if(!KillEffectRepository.killEffectSet.contains(killEffectName)) {
            return null;
        }

        KillEffect killEffect = KillEffectRepository.killEffectHashMap.get(killEffectName);

        List<String> killEffectLore = new ArrayList<>();

        String[] splitLore = killEffect.getLore().split("&n");

        killEffectLore.add(PluginRepository.pluginConfig.getLoreDisPlayerText() + ChatColor.WHITE + splitLore[0]);

        for (int j = 1; j < splitLore.length; j++) {
            killEffectLore.add(ChatColor.YELLOW + "        " + splitLore[j]);
        }

        killEffectLore.add(PluginRepository.pluginConfig.getCooldownDisplayText() + ChatColor.WHITE + killEffect.getCooldown() + "초");
        killEffectLore.add(PluginRepository.pluginConfig.getActiveTypeDisplayText() + ChatColor.WHITE + killEffect.getActiveType());
        killEffectLore.add(ChatColor.WHITE + "---------------------");
        killEffectLore.add(ChatColor.GOLD + "- 우클릭: 킬이펙트 추가");
        killEffectLore.add(ChatColor.DARK_GRAY + "KillEffect");


        return GUI.getCustomItemStack(Material.ENCHANTED_BOOK, ChatColor.GOLD + killEffect.getName(), killEffectLore);
    }

}
