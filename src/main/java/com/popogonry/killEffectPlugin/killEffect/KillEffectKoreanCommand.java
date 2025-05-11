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

public class KillEffectKoreanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        KillEffectService killEffectService = new KillEffectService();
        KillEffectRepository killEffectRepository = new KillEffectRepository();

        if(strings.length == 0) {

            // commandSender이 플레이어가 아니라면, 예외
            if(!(commandSender instanceof Player)) {
                commandSender.sendMessage(Reference.prefix_error + "플레이어 전용 커맨드입니다.");
                return false;
            }

            Player player = (Player) commandSender;
            KillEffectGUI.openKillEffectSetGUI(player, 1, (Player) commandSender, "normal");
        }
        if(commandSender.isOp()) {
            if(strings.length == 1) {
                if (strings[0].equalsIgnoreCase("리스트")) {
                    killEffectService.printKillEffectList(commandSender);
                }
                else if (strings[0].equalsIgnoreCase("로드")) {
                    killEffectRepository.loadKillEffectSet();
                    killEffectRepository.loadAllKillEffects();
                    commandSender.sendMessage(Reference.prefix_opMessage + "킬이펙트 전체를 불러왔습니다. (파일 -> 메모리)");
                }
                else if (strings[0].equalsIgnoreCase("세이브")) {
                    killEffectRepository.saveKillEffectSet();
                    killEffectRepository.saveAllKillEffects();
                    commandSender.sendMessage(Reference.prefix_opMessage + "킬이펙트 전체를 저장하였습니다. (메모리 -> 파일)");
                }
            }
            else if(strings.length == 2) {
                if (strings[0].equalsIgnoreCase("생성")) {
                    if (killEffectService.createKillEffect(strings[1], "mysticmobName", "설명", 0.0, KillEffectActiveType.ALL)) {
                        commandSender.sendMessage(Reference.prefix_opMessage + "생성 되었습니다.");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + "생성에 실패했습니다.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("삭제")) {
                    if (killEffectService.removeKillEffect(strings[1])) {
                        commandSender.sendMessage(Reference.prefix_opMessage + "삭제 되었습니다.");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + "삭제에 실패했습니다.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("로드")) {
                    if (killEffectRepository.loadKillEffect(strings[1])) {
                        commandSender.sendMessage(Reference.prefix_opMessage + strings[1] + " : 킬이펙트를 불러왔습니다. (파일 -> 메모리)");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " : Load Failed.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("세이브")) {
                    if (killEffectRepository.saveKillEffect(strings[1])) {
                        commandSender.sendMessage(Reference.prefix_opMessage + strings[1] + " : 킬이펙트를 저장하였습니다. (메모리 -> 파일)");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " : Save Failed.");
                    }
                }
                else if (strings[0].equalsIgnoreCase("관리")) {

                    if(!(commandSender instanceof Player)) {
                        commandSender.sendMessage(Reference.prefix_error + "플레이어 전용 커맨드입니다.");
                        return false;
                    }

                    Player player = Bukkit.getPlayer(strings[1]);
                    if (player == null) {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " 플레이어가 서버에 존재하지 않습니다.");
                        return false;
                    }
                    commandSender.sendMessage(String.valueOf(KillEffectGUI.openKillEffectSetGUI(player, 1, (Player) commandSender, "control")));
                }
                else if (strings[0].equalsIgnoreCase("쿠폰")) {
                    ItemStack killEffectBook = killEffectService.getKillEffectBook(strings[1]);
                    if (killEffectBook != null) {
                        Player player = (Player) commandSender;
                        player.getInventory().addItem(killEffectBook);
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + "존재 하지 않는 킬이펙트입니다.");
                    }
                }
            }
            else if(strings.length == 3) {
                if (strings[0].equalsIgnoreCase("추가")) {
                    Player player = Bukkit.getOfflinePlayer(strings[1]).getPlayer();
                    if (player == null) {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " 플레이어는 서버에 존재하지 않습니다.");
                        return false;
                    }
                    commandSender.sendMessage(String.valueOf(killEffectService.addKillEffectToUser(player, strings[2])));

                }
                else if (strings[0].equalsIgnoreCase("제거")) {
                    Player player = Bukkit.getOfflinePlayer(strings[1]).getPlayer();
                    if (player == null) {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " 플레이어는 서버에 존재하지 않습니다.");
                        return false;
                    }
                    commandSender.sendMessage(String.valueOf(killEffectService.removeKillEffectFromUser(player, strings[2])));
                }
                else if (strings[0].equalsIgnoreCase("설정")) {

                    if(!KillEffectRepository.killEffectSet.contains(strings[1])) {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " : 킬이펙트는 존재하지 않습니다.");
                        return false;
                    }

                    KillEffect killEffect = KillEffectRepository.killEffectHashMap.get(strings[1]);
                    killEffect.setMysticmobName(strings[2]);

                    if(killEffectService.updateKillEffect(strings[1], killEffect)) {
                        commandSender.sendMessage(Reference.prefix_opMessage + strings[1] + " : 킬이펙트의 미스틱 몹을 " + strings[2] + " 로 설정하였습니다.");
                    } else {
                        commandSender.sendMessage(Reference.prefix_error + strings[1] + " : 수정에 실패했습니다.");
                    }

                }
            }

        }











        return false;
    }
}
