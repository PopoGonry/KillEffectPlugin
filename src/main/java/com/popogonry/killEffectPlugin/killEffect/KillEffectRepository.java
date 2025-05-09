package com.popogonry.killEffectPlugin.killEffect;


import com.popogonry.killEffectPlugin.KillEffectPlugin;
import com.popogonry.killEffectPlugin.killEffect.dataConfig.KillEffectDataConfig;
import com.popogonry.killEffectPlugin.killEffect.dataConfig.KillEffectSetDataConfig;
import com.popogonry.killEffectPlugin.killEffect.dataConfig.UserKillEffectSetDataConfig;
import org.bukkit.Bukkit;

import java.io.File;
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
        File dir = new File(configBasePath + "/killEffects");
        if(!dir.exists()) dir.mkdirs();
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
        Set<String> killEffectSet = userKillEffectSetDataConfig.loadUserKillEffectSet(uuid);
        if(killEffectSet == null) {
            killEffectSet = new HashSet<>();
        }
        userKillEffectHashMap.put(uuid, killEffectSet);
    }

    public boolean storeKillEffect(String killEffectName) {
        // 메모리 상에 저장되어 있지 않으면,
        if(!killEffectHashMap.containsKey(killEffectName)) {
            return false;
        }

        KillEffectDataConfig killEffectDataConfig = new KillEffectDataConfig(configBasePath + "/killEffects", killEffectName + ".yml");
        killEffectDataConfig.storeKillEffectData(killEffectHashMap.get(killEffectName));
        killEffectHashMap.remove(killEffectName);
        return true;
    }
    public boolean saveKillEffect(String killEffectName) {
        // 메모리 상에 저장되어 있지 않으면,
        if(!killEffectHashMap.containsKey(killEffectName)) {
            return false;
        }

        KillEffectDataConfig killEffectDataConfig = new KillEffectDataConfig(configBasePath + "/killEffects", killEffectName + ".yml");
        killEffectDataConfig.storeKillEffectData(killEffectHashMap.get(killEffectName));
        return true;
    }
    public boolean loadKillEffect(String killEffectName) {

        // 파일이 존재 하지 않을 시, 예외
        File dataFile = new File(configBasePath + "/killEffects", killEffectName + ".yml");
        if (!dataFile.exists()) {
            killEffectSet.remove(killEffectName);
            saveKillEffectSet();
            return false;
        }

        KillEffectDataConfig killEffectDataConfig = new KillEffectDataConfig(configBasePath + "/killEffects", killEffectName + ".yml");

        killEffectHashMap.put(killEffectName, killEffectDataConfig.loadKillEffectData());
        killEffectSet.add(killEffectName);
        return true;
    }

    public void storeAllKillEffects() {
        Set<String> keySet = new HashSet<>(killEffectHashMap.keySet());
        for (String killEffectName : keySet) {
            storeKillEffect(killEffectName);
        }
    }

    public void saveAllKillEffects() {
        for (String killEffectName : killEffectHashMap.keySet()) {
            saveKillEffect(killEffectName);
        }
    }

    public void loadAllKillEffects() {
        HashSet<String> set = new HashSet<>(killEffectSet);

        for (String killEffectName : set) {
            loadKillEffect(killEffectName);
        }
    }
}
