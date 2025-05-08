package com.popogonry.killEffectPlugin.killEffect.dataConfig;

import com.popogonry.killEffectPlugin.Config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KillEffectSetDataConfig extends Config {
    public KillEffectSetDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
        loadDefaults();
    }

    public void storeKillEffectSet(Set<String> killEffectSet) {
        getConfig().set("KillEffectSet", new ArrayList<>(killEffectSet));
        super.store();
    }

    public HashSet<String> loadKillEffectSet() {
        return new HashSet<>(getConfig().getStringList("KillEffectSet"));
    }


    @Override
    public void loadDefaults() {
    }

    @Override
    public void applySettings() {
        getConfig().options().copyDefaults(true);
    }
}
