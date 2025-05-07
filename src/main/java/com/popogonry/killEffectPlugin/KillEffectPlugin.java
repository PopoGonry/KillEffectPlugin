package com.popogonry.killEffectPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class KillEffectPlugin extends JavaPlugin {

    private static KillEffectPlugin serverInstance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        serverInstance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static KillEffectPlugin getServerInstance() {
        return serverInstance;
    }

}
