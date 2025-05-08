package com.popogonry.killEffectPlugin;

import com.popogonry.killEffectPlugin.killEffect.KillEffectCommand;
import com.popogonry.killEffectPlugin.killEffect.KillEffectRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillEffectPlugin extends JavaPlugin {

    private static KillEffectPlugin serverInstance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        serverInstance = this;

        getServer().getPluginCommand("ke").setExecutor(new KillEffectCommand());

        // killEffect data load
        KillEffectRepository killEffectRepository = new KillEffectRepository();

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "KillEffect Data Load Start...");

        killEffectRepository.loadKillEffectSet();
        killEffectRepository.loadAllKillEffects();

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

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "KillEffect Data Store Complete!");

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + "KillEffect Plugin Disabled (Developer: PopoGonry)");

    }

    public static KillEffectPlugin getServerInstance() {
        return serverInstance;
    }

}
