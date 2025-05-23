package com.popogonry.killEffectPlugin;

import com.popogonry.killEffectPlugin.killEffect.*;
import com.popogonry.killEffectPlugin.killEffect.gui.KillEffectGUIEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillEffectPlugin extends JavaPlugin {

    private static KillEffectPlugin serverInstance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        serverInstance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new KillEffectEvent(), this);
        getServer().getPluginManager().registerEvents(new KillEffectGUIEvent(), this);


        getServer().getPluginCommand("ke").setExecutor(new KillEffectCommand());
        getServer().getPluginCommand("킬이펙트").setExecutor(new KillEffectKoreanCommand());

        // killEffect data load
        KillEffectRepository killEffectRepository = new KillEffectRepository();
        PluginRepository pluginRepository = new PluginRepository();

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "KillEffect Data Load Start...");

        killEffectRepository.loadKillEffectSet();
        killEffectRepository.loadAllKillEffects();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            killEffectRepository.loadUserKillEffectSet(onlinePlayer.getUniqueId());
            killEffectRepository.loadUserKillEffect(onlinePlayer.getUniqueId());

        }

        killEffectRepository.deleteInvalidYmlFiles();

        pluginRepository.loadPluginDataConfig();

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "KillEffect Data Load Complete!");

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "KillEffect Plugin Enabled (Developer: PopoGonry)");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // killEffect data store
        KillEffectRepository killEffectRepository = new KillEffectRepository();

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "KillEffect Data Store Start...");

        killEffectRepository.storeKillEffectSet();
        killEffectRepository.storeAllKillEffects();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            killEffectRepository.storeUserKillEffectSet(onlinePlayer.getUniqueId());
            killEffectRepository.storeUserKillEffect(onlinePlayer.getUniqueId());
        }

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "KillEffect Data Store Complete!");

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "KillEffect Plugin Disabled (Developer: PopoGonry)");

    }

    public static KillEffectPlugin getServerInstance() {
        return serverInstance;
    }

}
