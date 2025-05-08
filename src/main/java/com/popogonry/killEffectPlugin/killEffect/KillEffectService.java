package com.popogonry.killEffectPlugin.killEffect;

import org.bukkit.entity.Player;

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

    public void printKillEffectList(Player player) {
        for (String killEffectName : KillEffectRepository.killEffectSet) {
            player.sendMessage(killEffectName);
        }
    }

}
