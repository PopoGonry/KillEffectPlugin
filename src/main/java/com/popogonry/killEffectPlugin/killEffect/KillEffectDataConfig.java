package com.popogonry.killEffectPlugin.killEffect;

import com.popogonry.killEffectPlugin.Config;

import java.lang.module.Configuration;
import java.util.Set;
import java.util.UUID;

public class KillEffectDataConfig extends Config {

    public KillEffectDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
        loadDefaults();
    }

    public void storeKillEffectData(KillEffect killEffect) {
        getConfig().set("Name", killEffect.getName());
        getConfig().set("MysticMob-Name", killEffect.getMysticmobName());
        getConfig().set("Lore", killEffect.getLore());
        getConfig().set("Cooldown", killEffect.getCooldown());
        getConfig().set("Active-Type", killEffect.getActiveType());
        super.store();
    }

    public KillEffect loadKillEffectData() {
        return new KillEffect(
                getConfig().getString("Name"),
                getConfig().getString("MysticMob-Name"),
                getConfig().getString("Lore"),
                (Double) getConfig().get("Cooldown"),
                (KillEffectActiveType) getConfig().get("Active-Type")
        );
    }

    @Override
    public void loadDefaults() {
        String template =
                "Name: \n\n" +
                        "MysticMob-Name: \n\n" +
                        "Lore: \n\n" +
                        "Cooldown: \n\n" +
                        "# ActiveType List ( MOB / PLAYER / ALL ) \n" +
                        "Active-Type: ";

        writeInitialTemplateIfNotExists(template);
        reload();
    }

    @Override
    public void applySettings() {
        getConfig().options().copyDefaults(true);
    }
}
