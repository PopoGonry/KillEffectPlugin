package com.popogonry.killEffectPlugin.killEffect.dataConfig;

import com.popogonry.killEffectPlugin.Config;
import com.popogonry.killEffectPlugin.killEffect.KillEffect;
import com.popogonry.killEffectPlugin.killEffect.KillEffectActiveType;

import java.util.UUID;

public class UserKillEffectDataConfig extends Config {
    public UserKillEffectDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
//        loadDefaults();
    }

    public void storeUserKillEffectData(UUID uuid, String killEffectName) {
        getConfig().set(uuid.toString(), killEffectName);
        super.store();
    }

    public String loadUserKillEffectData(UUID uuid) {
        return getConfig().getString(uuid.toString());
    }

    @Override
    public void loadDefaults() {
    }

    @Override
    public void applySettings() {
        getConfig().options().copyDefaults(true);
    }
}
