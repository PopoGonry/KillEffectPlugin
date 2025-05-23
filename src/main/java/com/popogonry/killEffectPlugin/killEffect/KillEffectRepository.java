package com.popogonry.killEffectPlugin.killEffect;


import com.popogonry.killEffectPlugin.KillEffectPlugin;
import com.popogonry.killEffectPlugin.Reference;
import com.popogonry.killEffectPlugin.killEffect.dataConfig.KillEffectDataConfig;
import com.popogonry.killEffectPlugin.killEffect.dataConfig.KillEffectSetDataConfig;
import com.popogonry.killEffectPlugin.killEffect.dataConfig.UserKillEffectDataConfig;
import com.popogonry.killEffectPlugin.killEffect.dataConfig.UserKillEffectSetDataConfig;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.*;

public class KillEffectRepository {

    private static final String KILL_EFFECT_SET_NAME = "killEffectSet.yml";
    private static final String USER_KILL_EFFECT_SET_NAME = "userKillEffectSet.yml";
    private static final String USER_KILL_EFFECT_NAME = "userKillEffect.yml";
    private final String configBasePath;

    private final KillEffectSetDataConfig killEffectSetDataConfig;
    private final UserKillEffectSetDataConfig userKillEffectSetDataConfig;
    private final UserKillEffectDataConfig userKillEffectDataConfig;

    public static HashSet<String> killEffectSet = new HashSet<>();
    public static HashMap<String, KillEffect> killEffectHashMap = new HashMap<>();
    public static HashMap<UUID, Set<String>> userKillEffectSetHashMap = new HashMap<>();
    public static HashMap<UUID, String> userKillEffectHashMap = new HashMap<>();



    public KillEffectRepository() {
        this.configBasePath = KillEffectPlugin.getServerInstance().getDataFolder().getAbsolutePath();
        this.killEffectSetDataConfig = new KillEffectSetDataConfig(configBasePath, KILL_EFFECT_SET_NAME);
        this.userKillEffectSetDataConfig = new UserKillEffectSetDataConfig(configBasePath, USER_KILL_EFFECT_SET_NAME);
        this.userKillEffectDataConfig = new UserKillEffectDataConfig(configBasePath, USER_KILL_EFFECT_NAME);
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
        userKillEffectSetDataConfig.storeUserKillEffectSet(uuid, userKillEffectSetHashMap.get(uuid));
        userKillEffectSetHashMap.remove(uuid);
    }

    public void saveUserKillEffectSet(UUID uuid) {
        userKillEffectSetDataConfig.storeUserKillEffectSet(uuid, userKillEffectSetHashMap.get(uuid));
    }

    public void loadUserKillEffectSet(UUID uuid) {
        Set<String> killEffectSet = userKillEffectSetDataConfig.loadUserKillEffectSet(uuid);
        if(killEffectSet == null) {
            killEffectSet = new HashSet<>();
        }
        userKillEffectSetHashMap.put(uuid, killEffectSet);
    }

    public boolean storeUserKillEffect(UUID uuid) {
        userKillEffectDataConfig.storeUserKillEffectData(uuid, userKillEffectHashMap.getOrDefault(uuid, ""));
        userKillEffectHashMap.remove(uuid);
        return true;
    }

    public boolean saveUserKillEffect(UUID uuid) {
        userKillEffectDataConfig.storeUserKillEffectData(uuid, userKillEffectHashMap.getOrDefault(uuid, ""));
        return true;
    }

    public boolean loadUserKillEffect(UUID uuid) {
        String killEffectName = userKillEffectDataConfig.loadUserKillEffectData(uuid);
        if(killEffectName == null || killEffectName.equals("")) {
            return false;
        }
        userKillEffectHashMap.put(uuid, killEffectName);
        return true;
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

    public void deleteInvalidYmlFiles() {
        File directory = new File(configBasePath + "/killEffects");

        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".yml"));
        if (files == null) {
            return;
        }

        for (File file : files) {
            String fileNameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");

            if (!killEffectSet.contains(fileNameWithoutExtension)) {
                if (file.delete()) {
                    Bukkit.getConsoleSender().sendMessage(Reference.prefix_error + file.getName() + " 파일이 삭제되었습니다.");
                } else {
                    Bukkit.getConsoleSender().sendMessage(Reference.prefix_error + file.getName() + " 파일 삭제에 실패했습니다.");
                }
            }
        }
    }
}


