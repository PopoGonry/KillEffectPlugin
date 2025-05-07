package com.popogonry.killEffectPlugin.killEffect;

import com.popogonry.killEffectPlugin.Config;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class KillEffectSetDataConfig extends Config {
    public KillEffectSetDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
        loadDefaults();
    }

    public void storeKillEffectSet(Set<String> killEffectSet) {
        getConfig().set("KillEffectSet", killEffectSet);
        super.store();
    }

    public HashSet<String> loadKillEffectSet() {
        return (HashSet<String>) getConfig().getList("KillEffectSet");
    }

    @Override
    public void loadDefaults() {
    }

    @Override
    public void applySettings() {
        getConfig().options().copyDefaults(true);
    }
}
