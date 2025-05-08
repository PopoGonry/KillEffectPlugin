package com.popogonry.killEffectPlugin.killEffect;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KillEffectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        KillEffectService killEffectService = new KillEffectService();
        KillEffectRepository killEffectRepository = new KillEffectRepository();


        if(strings.length == 1) {
            if(commandSender.isOp()) {
                if(strings[0].equalsIgnoreCase("list")) {
                    killEffectService.printKillEffectList((Player) commandSender);
                }
                else if(strings[0].equalsIgnoreCase("store")) {
                }
                else if(strings[0].equalsIgnoreCase("load")) {

                }
                else if(strings[0].equalsIgnoreCase("save")) {

                }

                else if(strings[0].equalsIgnoreCase("storeAll")) {
                    killEffectRepository.storeKillEffectSet();
                    killEffectRepository.storeAllKillEffects();
                }
                else if(strings[0].equalsIgnoreCase("loadAll")) {
                    killEffectRepository.loadKillEffectSet();
                    killEffectRepository.loadAllKillEffects();
                }
                else if(strings[0].equalsIgnoreCase("saveAll")) {
                    killEffectRepository.saveKillEffectSet();
                    killEffectRepository.saveAllKillEffects();
                }


            }
        }
        else if(strings.length == 2) {
            if(commandSender.isOp()) {
                if(strings[0].equalsIgnoreCase("add")) {
                    if(killEffectService.createKillEffect(strings[1], "mysticmobName", "lore", 10.0, KillEffectActiveType.ALL)) {
                        commandSender.sendMessage("추가되었습니다.");
                    }
                    else {
                        commandSender.sendMessage("추가 실패했습니다.");
                    }
                }
                else if(strings[0].equalsIgnoreCase("remove")) {
                    if(killEffectService.removeKillEffect(strings[1])) {
                        commandSender.sendMessage("삭제되었습니다.");
                    }
                    else {
                        commandSender.sendMessage("삭제 실패했습니다.");
                    }
                }
                else if(strings[0].equalsIgnoreCase("update")) {
                    if(killEffectService.updateKillEffect(strings[1], new KillEffect("name2", "mysticmobName2", "lore2", 10.0, KillEffectActiveType.ALL))) {
                        commandSender.sendMessage("수정되었습니다.");
                    }
                    else {
                        commandSender.sendMessage("수정 실패했습니다.");
                    }
                }

            }
        }

        return false;
    }
}
