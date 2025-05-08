package com.popogonry.killEffectPlugin.killEffect.dataConfig;

import com.popogonry.killEffectPlugin.Config;
import com.popogonry.killEffectPlugin.killEffect.KillEffect;
import com.popogonry.killEffectPlugin.killEffect.KillEffectActiveType;

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
        getConfig().set("Active-Type", killEffect.getActiveType().toString());
        super.store();
    }

    public KillEffect loadKillEffectData() {
        return new KillEffect(
                getConfig().getString("Name"),
                getConfig().getString("MysticMob-Name"),
                getConfig().getString("Lore"),
                (Double) getConfig().get("Cooldown"),
                KillEffectActiveType.valueOf(getConfig().getString("Active-Type"))
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
