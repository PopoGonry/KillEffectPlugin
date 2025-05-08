package com.popogonry.killEffectPlugin;

import com.popogonry.killEffectPlugin.killEffect.KillEffectCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillEffectPlugin extends JavaPlugin {

    private static KillEffectPlugin serverInstance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        serverInstance = this;

        getServer().getPluginCommand("ke").setExecutor(new KillEffectCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static KillEffectPlugin getServerInstance() {
        return serverInstance;
    }

}
