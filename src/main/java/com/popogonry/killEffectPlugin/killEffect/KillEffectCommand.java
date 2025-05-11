package com.popogonry.killEffectPlugin.killEffect;

import com.popogonry.killEffectPlugin.Reference;
import com.popogonry.killEffectPlugin.killEffect.gui.KillEffectGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KillEffectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!commandSender.isOp()) return false;

        KillEffectService killEffectService = new KillEffectService();
        KillEffectRepository killEffectRepository = new KillEffectRepository();

        if (strings.length == 1) {
            if (commandSender.isOp()) {
                if (strings[0].equalsIgnoreCase("list")) {
                    killEffectService.printKillEffectList(commandSender);
                } else if (strings[0].equalsIgnoreCase("userList")) {
                    killEffectService.printUserKillEffectList(commandSender);
                } else if (strings[0].equalsIgnoreCase("storeAll")) {
                    killEffectRepository.storeKillEffectSet();
                    killEffectRepository.storeAllKillEffects();
                    commandSender.sendMessage(Reference.prefix_opMessage + "Store Complete.");
                } else if (strings[0].equalsIgnoreCase("loadAll")) {
                    killEffectRepository.loadKillEffectSet();
                    killEffectRepository.loadAllKillEffects();
                    commandSender.sendMessage(Reference.prefix_opMessage + "Load Complete.");


                } else if (strings[0].equalsIgnoreCase("saveAll")) {
                    killEffectRepository.saveKillEffectSet();
                    killEffectRepository.saveAllKillEffects();
                    commandSender.sendMessage(Reference.prefix_opMessage + "Save Complete.");


                } else if (strings[0].equalsIgnoreCase("test")) {
                    for(int i = 0; i < 36; i++){
                        killEffectService.createKillEffect("test" + i, "mysticmobName", "lore", 10.0, KillEffectActiveType.ALL);
                        killEffectService.addKillEffectToUser((Player) commandSender, "test" + i);
                    }
                }
            }
            if(strings[0].equalsIgnoreCase("gui")) {
                Player player = (Player) commandSender;
                KillEffectGUI.openKillEffectSetGUI(player, 1, (Player) commandSender, "normal");
            }



        } else if (strings.length == 2) {
            if (commandSender.isOp()) {
                if (strings[0].equalsIgnoreCase("store")) {
                    if (killEffectRepository.storeKillEffect(strings[1])) {
                        commandSender.sendMessage(Reference.prefix_opMessage + strings[1] + " : Store Succeed.");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " : Store Failed.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("load")) {
                    if (killEffectRepository.loadKillEffect(strings[1])) {
                        commandSender.sendMessage(Reference.prefix_opMessage + strings[1] + " : Load Succeed.");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " : Load Failed.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("save")) {
                    if (killEffectRepository.saveKillEffect(strings[1])) {
                        commandSender.sendMessage(Reference.prefix_opMessage + strings[1] + " : Save Succeed.");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " : Save Failed.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("add")) {
                    if (killEffectService.createKillEffect(strings[1], "mysticmobName", "lore테스트스테스트 세트스 테스트", 10.0, KillEffectActiveType.ALL)) {
                        commandSender.sendMessage(Reference.prefix_opMessage + "추가되었습니다.");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + "추가 실패했습니다.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("remove")) {
                    if (killEffectService.removeKillEffect(strings[1])) {
                        commandSender.sendMessage(Reference.prefix_opMessage + "삭제되었습니다.");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + "삭제 실패했습니다.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("update")) {
                    if (killEffectService.updateKillEffect(strings[1], new KillEffect("name2", "mysticmobName2", "lore2", 10.0, KillEffectActiveType.ALL))) {
                        commandSender.sendMessage(Reference.prefix_opMessage + "수정되었습니다.");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + "수정 실패했습니다.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("get")) {
                    ItemStack killEffectBook = killEffectService.getKillEffectBook(strings[1]);
                    if (killEffectBook != null) {
                        Player player = (Player) commandSender;
                        player.getInventory().addItem(killEffectBook);
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + "존재 하지 않는 킬이펙트입니다.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("set")) {
                    Player player = Bukkit.getOfflinePlayer(strings[1]).getPlayer();
                    if (player == null) {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " 플레이어는 서버에 존재하지 않습니다.");
                        return false;
                    }
                    commandSender.sendMessage(String.valueOf(killEffectService.removeUserKillEffect(player)));
                }
                else if (strings[0].equalsIgnoreCase("control")) {
                    Player player = Bukkit.getOfflinePlayer(strings[1]).getPlayer();
                    if (player == null) {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " 플레이어는 서버에 존재하지 않습니다.");
                        return false;
                    }
                    commandSender.sendMessage(String.valueOf(KillEffectGUI.openKillEffectSetGUI(player, 1, (Player) commandSender, "control")));
                }

            }
        } else if (strings.length == 3) {
            if (commandSender.isOp()) {
                if (strings[0].equalsIgnoreCase("add")) {
                    Player player = Bukkit.getOfflinePlayer(strings[1]).getPlayer();
                    if (player == null) {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " 플레이어는 서버에 존재하지 않습니다.");
                        return false;
                    }
                    commandSender.sendMessage(String.valueOf(killEffectService.addKillEffectToUser(player, strings[2])));

                }
                else if (strings[0].equalsIgnoreCase("remove")) {
                    Player player = Bukkit.getOfflinePlayer(strings[1]).getPlayer();
                    if (player == null) {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " 플레이어는 서버에 존재하지 않습니다.");
                        return false;
                    }
                    commandSender.sendMessage(String.valueOf(killEffectService.removeKillEffectFromUser(player, strings[2])));
                }
                else if (strings[0].equalsIgnoreCase("set")) {
                    Player player = Bukkit.getOfflinePlayer(strings[1]).getPlayer();
                    if (player == null) {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " 플레이어는 서버에 존재하지 않습니다.");
                        return false;
                    }
                    commandSender.sendMessage(String.valueOf(killEffectService.setUserKillEffect(player, strings[2])));
                }
            }
        }
        return false;
    }
}