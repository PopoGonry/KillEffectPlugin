package com.popogonry.killEffectPlugin.killEffect.gui;

import com.popogonry.killEffectPlugin.GUI;
import com.popogonry.killEffectPlugin.PluginRepository;
import com.popogonry.killEffectPlugin.Reference;
import com.popogonry.killEffectPlugin.killEffect.KillEffect;
import com.popogonry.killEffectPlugin.killEffect.KillEffectRepository;
import com.popogonry.killEffectPlugin.killEffect.KillEffectService;
import dev.lone.itemsadder.api.CustomStack;
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
            inventory = Bukkit.createInventory(player, 54, PluginRepository.pluginConfig.getKillEffectGUIName());
        }

        else if (type.equalsIgnoreCase("control")) {
            inventory = Bukkit.createInventory(player, 54, PluginRepository.pluginConfig.getKillEffectControlGUIName());
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

            userKillEffectLore.add(PluginRepository.pluginConfig.getLoreDisPlayerText() + ChatColor.WHITE + splitLore[0]);

            for (int j = 1; j < splitLore.length; j++) {
                userKillEffectLore.add(ChatColor.YELLOW + "        " + splitLore[j]);
            }

            userKillEffectLore.add(PluginRepository.pluginConfig.getCooldownDisplayText() + ChatColor.WHITE + userKillEffect.getCooldown() + "초");
            userKillEffectLore.add(PluginRepository.pluginConfig.getActiveTypeDisplayText() + ChatColor.WHITE + userKillEffect.getActiveType());
            userKillEffectLore.add(ChatColor.WHITE + "---------------------");
            userKillEffectLore.add(ChatColor.GOLD + "- 좌클릭: 킬이펙트 장착 해제");

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

            if(killEffect == null) {
                KillEffectService killEffectService = new KillEffectService();
                killEffectService.removeKillEffectFromUser(player, killEffectNameList.get(i));
                continue;
            }

            itemMeta.setDisplayName(ChatColor.GOLD + killEffect.getName());

            List<String> lore = new ArrayList<>();

            String[] splitLore = killEffect.getLore().split("&n");

            lore.add(PluginRepository.pluginConfig.getLoreDisPlayerText() + ChatColor.WHITE + splitLore[0]);

            for (int j = 1; j < splitLore.length; j++) {
                lore.add(ChatColor.YELLOW + "        " + splitLore[j]);
            }

            lore.add(PluginRepository.pluginConfig.getCooldownDisplayText() + ChatColor.WHITE + killEffect.getCooldown() + "초");
            lore.add(PluginRepository.pluginConfig.getActiveTypeDisplayText() + ChatColor.WHITE + killEffect.getActiveType());
            lore.add(ChatColor.WHITE + "---------------------");
            lore.add(ChatColor.GOLD + "- 좌클릭: 킬이펙트 장착");

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

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "Amount of KillEffects: " + killEffectNameList.size());
        lore.add(ChatColor.GOLD + "Player: " + player.getName());


        ItemStack leftPage = GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page - 1));
        ItemStack rightPage = GUI.getCustomItemStack(Material.PAPER, Reference.prefix + "To " + (page + 1));
        ItemStack information = GUI.getCustomItemStack(Material.EMERALD, Reference.prefix + "Page " + page + " / " + maxPage, lore);

        if(CustomStack.isInRegistry(PluginRepository.pluginConfig.getKillEffectGUILeftPageItem())) {
            leftPage.setType(CustomStack.getInstance(PluginRepository.pluginConfig.getKillEffectGUILeftPageItem()).getItemStack().getType());
        }
        if(CustomStack.isInRegistry(PluginRepository.pluginConfig.getKillEffectGUIRightPageItem())) {
            rightPage.setType(CustomStack.getInstance(PluginRepository.pluginConfig.getKillEffectGUIRightPageItem()).getItemStack().getType());

        }
        if(CustomStack.isInRegistry(PluginRepository.pluginConfig.getKillEffectGUIInformationItem())) {
            information.setType(CustomStack.getInstance(PluginRepository.pluginConfig.getKillEffectGUIInformationItem()).getItemStack().getType());

        }

        inventory.setItem(49, information);

        if(page > 1) {
            inventory.setItem(48, leftPage);
        }

        if(page < maxPage) {
            inventory.setItem(50, rightPage);
        }

        viewPlayer.openInventory(inventory);

        return true;
    }



}
