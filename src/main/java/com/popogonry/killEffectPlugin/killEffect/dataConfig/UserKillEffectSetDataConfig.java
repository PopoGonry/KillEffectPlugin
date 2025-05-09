package com.popogonry.killEffectPlugin.killEffect.dataConfig;

import com.popogonry.killEffectPlugin.Config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserKillEffectSetDataConfig extends Config {
    public UserKillEffectSetDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
        loadDefaults();
    }

    public void storeUserKillEffectSet(UUID uuid, Set<String> killEffectSet) {
        getConfig().set(uuid.toString(), new ArrayList<>(killEffectSet));
        super.store();
    }

    public Set<String> loadUserKillEffectSet(UUID uuid) {
        return new HashSet<>(getConfig().getStringList(uuid.toString()));
    }

    public boolean hasUserKillEffectSet(UUID uuid) {
        return getConfig().contains(uuid.toString());
    }

    public void removeUserKillEffectSet(UUID uuid) {
        getConfig().set(uuid.toString(), null);
    }

    @Override
    public void loadDefaults() {
    }

    @Override
    public void applySettings() {
        getConfig().options().copyDefaults(true);
    }

}
