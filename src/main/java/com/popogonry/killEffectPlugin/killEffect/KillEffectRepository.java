package com.popogonry.killEffectPlugin.killEffect;


import com.popogonry.killEffectPlugin.KillEffectPlugin;

import java.util.*;

public class KillEffectRepository {

    private static final String KILL_EFFECT_NAME = "killEffect.yml";
    private static final String USER_KILL_EFFECT_NAME = "userKillEffect.yml";
    private final String configBasePath;

    private final KillEffectSetDataConfig killEffectSetDataConfig;
    private final UserKillEffectSetDataConfig userKillEffectSetDataConfig;

    public static HashSet<String> killEffectSet = new HashSet<>();
    public static HashMap<String, KillEffect> killEffectHashMap = new HashMap<>();
    public static HashMap<UUID, Set<String>> userKillEffectHashMap = new HashMap<>();

    public KillEffectRepository() {
        this.configBasePath = KillEffectPlugin.getServerInstance().getDataFolder().getAbsolutePath();
        this.killEffectSetDataConfig = new KillEffectSetDataConfig(configBasePath, KILL_EFFECT_NAME);
        this.userKillEffectSetDataConfig = new UserKillEffectSetDataConfig(configBasePath, USER_KILL_EFFECT_NAME);
    }

    public void reloadConfig() {
        killEffectSetDataConfig.reload();
    }
    public void saveConfig() {
        killEffectSetDataConfig.store();
    }

    public void storeKillEffectSet() {
        killEffectSetDataConfig.storeKillEffectSet(killEffectSet);
        killEffectSet.clear();
    }

    public void saveKillEffectSet() {
        killEffectSetDataConfig.storeKillEffectSet(killEffectSet);
    }

    public void loadKillEffectSet() {
        killEffectSet = killEffectSetDataConfig.loadKillEffectSet();
        if(killEffectSet == null) {
            killEffectSet = new HashSet<>();
        }
    }

    public void storeUserKillEffectSet(UUID uuid) {
        userKillEffectSetDataConfig.storeUserKillEffectSet(uuid, userKillEffectHashMap.get(uuid));
        userKillEffectHashMap.remove(uuid);
    }

    public void saveUserKillEffectSet(UUID uuid) {
        userKillEffectSetDataConfig.storeUserKillEffectSet(uuid, userKillEffectHashMap.get(uuid));
    }

    public void loadUserKillEffectSet(UUID uuid) {
        userKillEffectHashMap.put(uuid, userKillEffectSetDataConfig.loadUserKillEffectSet(uuid));
    }

    public void storeKillEffect(KillEffect killEffect) {
        private KillEffectDataConfig killEffectDataConfig = new KillEffectDataConfig(configBasePath + "\")
    }


}
