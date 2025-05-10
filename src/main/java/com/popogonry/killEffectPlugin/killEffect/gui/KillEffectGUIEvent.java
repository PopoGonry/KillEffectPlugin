package com.popogonry.killEffectPlugin.killEffect.gui;

import com.popogonry.killEffectPlugin.Reference;
import com.popogonry.killEffectPlugin.killEffect.KillEffectService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KillEffectGUIEvent implements Listener {
    @EventHandler
    public static void onClickKillEffectsGUI(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(Reference.prefix_normal + "Kill Effect GUI")
                && event.getCurrentItem() != null
                && event.getCurrentItem().getType() != Material.AIR) {

            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getInventory();

            int slot = event.getRawSlot();

            KillEffectService killEffectService = new KillEffectService();

            String[] strings1 = inventory.getItem(49).getItemMeta().getDisplayName().split("/");
            String[] strings2 = strings1[0].split(" ");
            int page = Integer.parseInt(strings2[1].replaceAll(" ", ""));

            // 장착 중 KillEffect
            if(slot == 4) {
                if(event.getClick().isLeftClick()) {
                    if (killEffectService.removeUserKillEffect(player)) {
                        KillEffectGUI.openKillEffectSetGUI(player, page, player, "normal");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    }
                    else {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }

                }
            }

            // KillEffect List
            else if(9 <= slot && slot <= 44) {
                ItemStack itemStack = inventory.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(event.getClick().isLeftClick()) {
                    if (killEffectService.setUserKillEffect(player, itemMeta.getDisplayName().replaceAll(ChatColor.GOLD.toString(), ""))) {
                        KillEffectGUI.openKillEffectSetGUI(player, page, player, "normal");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    }
                    else {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
            }

            else if(48 <= slot && slot <= 50) {
                ItemStack itemStack = inventory.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta.getDisplayName().contains("To")) {
                    String[] strings = itemMeta.getDisplayName().split(" ");
                    KillEffectGUI.openKillEffectSetGUI(player, Integer.parseInt(strings[1]), player, "normal");
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }
            }
            // Player Inventory
            else if(54 <= slot && slot <= 89) {

            }
        }
    }@EventHandler
    public static void onClickKillEffectsControlGUI(InventoryClickEvent event) {
        if(event.getView().getTitle().contains("Kill Effect Control GUI")
                && event.getCurrentItem() != null
                && event.getCurrentItem().getType() != Material.AIR) {

            event.setCancelled(true);

            Inventory inventory = event.getInventory();

            Player viewPlayer = (Player) event.getWhoClicked();
            Player player = Bukkit.getPlayer(event.getView().getTitle().split(" ")[1].replaceAll(" ", ""));

            if(player == null) {
                return;
            }

            int slot = event.getRawSlot();

            KillEffectService killEffectService = new KillEffectService();

            String[] strings1 = inventory.getItem(49).getItemMeta().getDisplayName().split("/");
            String[] strings2 = strings1[0].split(" ");
            int page = Integer.parseInt(strings2[1].replaceAll(" ", ""));

            // 장착 중 KillEffect
            if(slot == 4) {
                if(event.getClick().isLeftClick()) {
                    if (killEffectService.removeUserKillEffect(player)) {
                        KillEffectGUI.openKillEffectSetGUI(player, page, viewPlayer, "control");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    }
                    else {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
                else if(event.getClick().isRightClick() && event.getClick().isShiftClick()) {
                    ItemStack itemStack = inventory.getItem(slot);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if (killEffectService.removeUserKillEffect(player)
                            && killEffectService.removeKillEffectFromUser(player, itemMeta.getDisplayName().replaceAll(ChatColor.GOLD.toString(), ""))) {

                        KillEffectGUI.openKillEffectSetGUI(player, page, viewPlayer, "control");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    }
                    else {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
            }

            // KillEffect List
            else if(9 <= slot && slot <= 44) {
                ItemStack itemStack = inventory.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(event.getClick().isLeftClick()) {
                    if (killEffectService.setUserKillEffect(player, itemMeta.getDisplayName().replaceAll(ChatColor.GOLD.toString(), ""))) {
                        KillEffectGUI.openKillEffectSetGUI(player, page, viewPlayer, "control");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    }
                    else {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
                else if(event.getClick().isRightClick() && event.getClick().isShiftClick()) {
                    if (killEffectService.removeKillEffectFromUser(player, itemMeta.getDisplayName().replaceAll(ChatColor.GOLD.toString(), ""))) {
                        KillEffectGUI.openKillEffectSetGUI(player, page, viewPlayer, "control");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    }
                    else {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
            }

            else if(48 <= slot && slot <= 50) {
                ItemStack itemStack = inventory.getItem(slot);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta.getDisplayName().contains("To")) {
                    String[] strings = itemMeta.getDisplayName().split(" ");
                    KillEffectGUI.openKillEffectSetGUI(player, Integer.parseInt(strings[1]), viewPlayer, "control");
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }
            }
            // Player Inventory
            else if(54 <= slot && slot <= 89) {

            }
        }
    }
}
