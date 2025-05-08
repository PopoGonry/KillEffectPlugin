package com.popogonry.killEffectPlugin.killEffect;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KillEffectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(strings.length == 1) {
            if(commandSender.isOp()) {
//                if(strings[0].equalsIgnoreCase("show")) {
//                    commandSender.sendMessage(ShopRepository.shopDataHashMap.toString());
//                    commandSender.sendMessage(ShopRepository.shopNameSet.toString());
//                    return true;
//                }
                if(strings[0].equalsIgnoreCase("list")) {

                }
            }
        }



        return false;
    }
}
