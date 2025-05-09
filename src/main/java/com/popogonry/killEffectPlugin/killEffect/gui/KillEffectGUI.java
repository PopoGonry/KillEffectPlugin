package com.popogonry.killEffectPlugin.killEffect.gui;

import com.popogonry.killEffectPlugin.GUI;
import com.popogonry.killEffectPlugin.Reference;
import com.popogonry.killEffectPlugin.killEffect.KillEffect;
import com.popogonry.killEffectPlugin.killEffect.KillEffectRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class KillEffectGUI {
    public static boolean openKillEffectSetGUI(Player player, int page) {
        Inventory inventory = Bukkit.createInventory(player, 54, Reference.prefix_normal + "Kill Effect GUI");

        Set<String> userKillEffectSet = new HashSet<>(KillEffectRepository.userKillEffectSetHashMap.get(player.getUniqueId()));

        String userKillEffect = KillEffectRepository.userKillEffectHashMap.get(player.getUniqueId());
        if(userKillEffect != null) {
            userKillEffectSet.remove(userKillEffect);
        }

        ArrayList<String> killEffectNameList = new ArrayList<>(userKillEffectSet);
        Collections.sort(killEffectNameList);

        if(userKillEffect != null) {
            killEffectNameList.add(0, userKillEffect);
        }

        for (int i = 0 + (45*(page-1)); i < 45 + (45*(page-1)) && i < killEffectNameList.size(); i++) {
            ItemStack itemStack = new ItemStack(Material.BOOK);

            if(i == 0) {
                itemStack = new ItemStack(Material.ENCHANTED_BOOK);
            }

            ItemMeta itemMeta = itemStack.getItemMeta();
            KillEffect killEffect = KillEffectRepository.killEffectHashMap.get(killEffectNameList.get(i));

            itemMeta.setDisplayName(ChatColor.GOLD + killEffect.getName());

            List<String> lore = new ArrayList<>();

            lore.add(ChatColor.YELLOW + "설명: " + killEffect.getLore());
            lore.add(ChatColor.YELLOW + "쿨타임: " + killEffect.getCooldown());
            lore.add(ChatColor.YELLOW + "발동 타입: " + killEffect.getActiveType());

            if(i != 0) {
                lore.add(ChatColor.WHITE + "---------------------");
                lore.add(ChatColor.YELLOW + "- 좌클릭: 킬이펙트 장착");
            }

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            inventory.addItem(itemStack);
        }


        // 48 49 50
        int maxPage = killEffectNameList.size() / 45;
        maxPage += killEffectNameList.size() % 45 == 0 ? 0 : 1;

        inventory.setItem(49, GUI.getCustomItemStack(Material.EMERALD, Reference.prefix + "Page " + page + " / " + maxPage, Collections.singletonList(ChatColor.GOLD + "Amount of KillEffects: " + killEffectNameList.size())));

        if(page > 1) {
            inventory.setItem(48, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page - 1)));
        }

        if(page < maxPage) {
            inventory.setItem(50, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page + 1)));
        }

        player.openInventory(inventory);

        return true;
    }



}
