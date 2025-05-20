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
    public static boolean openKillEffectSetGUI(Player player, int page, Player viewPlayer, String type) {
        Inventory inventory;

        if(type.equalsIgnoreCase("normal")) {
            viewPlayer = player;
            inventory = Bukkit.createInventory(player, 54, Reference.prefix_normal + "Kill Effect GUI");
        }

        else if (type.equalsIgnoreCase("control")) {
            inventory = Bukkit.createInventory(player, 54, Reference.prefix_normal +  player.getName() + " : Kill Effect Control GUI");
        }
        else {
            return false;
        }

        Set<String> userKillEffectSet = new HashSet<>(KillEffectRepository.userKillEffectSetHashMap.get(player.getUniqueId()));

        String userKillEffectName = KillEffectRepository.userKillEffectHashMap.get(player.getUniqueId());
        if(userKillEffectName != null) {
            userKillEffectSet.remove(userKillEffectName);

            KillEffect userKillEffect = KillEffectRepository.killEffectHashMap.get(userKillEffectName);

            List<String> userKillEffectLore = new ArrayList<>();

            String[] splitLore = userKillEffect.getLore().split("&n");

            userKillEffectLore.add(ChatColor.YELLOW + "- 설명: " + splitLore[0]);

            for (int j = 1; j < splitLore.length; j++) {
                userKillEffectLore.add(ChatColor.YELLOW + "        " + splitLore[j]);
            }

            userKillEffectLore.add(ChatColor.YELLOW + "- 쿨타임: " + userKillEffect.getCooldown() + "초");
            userKillEffectLore.add(ChatColor.YELLOW + "- 발동 타입: " + userKillEffect.getActiveType());
            userKillEffectLore.add(ChatColor.WHITE + "---------------------");
            userKillEffectLore.add(ChatColor.YELLOW + "- 좌클릭: 킬이펙트 장착 해제");

            if (type.equalsIgnoreCase("control")) {
                userKillEffectLore.add(ChatColor.RED + "- 쉬프트 + 우클릭: 킬이펙트 삭제");
            }


            inventory.setItem(4, GUI.getCustomItemStack(Material.ENCHANTED_BOOK, ChatColor.GOLD + userKillEffect.getName(), userKillEffectLore));
        }

        ArrayList<String> killEffectNameList = new ArrayList<>(userKillEffectSet);
        Collections.sort(killEffectNameList);


        for (int i = 0 + (36*(page-1)); i < 36 + (36*(page-1)) && i < killEffectNameList.size(); i++) {
            ItemStack itemStack = new ItemStack(Material.BOOK);

            ItemMeta itemMeta = itemStack.getItemMeta();
            KillEffect killEffect = KillEffectRepository.killEffectHashMap.get(killEffectNameList.get(i));

            itemMeta.setDisplayName(ChatColor.GOLD + killEffect.getName());

            List<String> lore = new ArrayList<>();

            String[] splitLore = killEffect.getLore().split("&n");
            System.out.println(killEffect.getLore());
            System.out.println(splitLore.toString());

            lore.add(ChatColor.YELLOW + "- 설명: " + splitLore[0]);

            for (int j = 1; j < splitLore.length; j++) {
                lore.add(ChatColor.YELLOW + "        " + splitLore[j]);
            }

            lore.add(ChatColor.YELLOW + "- 쿨타임: " + killEffect.getCooldown() + "초");
            lore.add(ChatColor.YELLOW + "- 발동 타입: " + killEffect.getActiveType());
            lore.add(ChatColor.WHITE + "---------------------");
            lore.add(ChatColor.YELLOW + "- 좌클릭: 킬이펙트 장착");

            if (type.equalsIgnoreCase("control")) {
                lore.add(ChatColor.RED + "- 쉬프트 + 우클릭: 킬이펙트 삭제");
            }

            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);

            inventory.setItem((i%36)+9, itemStack);
        }


        // 48 49 50
        int maxPage = killEffectNameList.size() / 36;
        maxPage += killEffectNameList.size() % 36 == 0 ? 0 : 1;

        inventory.setItem(49, GUI.getCustomItemStack(Material.EMERALD, Reference.prefix + "Page " + page + " / " + maxPage, Collections.singletonList(ChatColor.GOLD + "Amount of KillEffects: " + killEffectNameList.size())));

        if(page > 1) {
            inventory.setItem(48, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page - 1)));
        }

        if(page < maxPage) {
            inventory.setItem(50, GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page + 1)));
        }

        viewPlayer.openInventory(inventory);

        return true;
    }



}
