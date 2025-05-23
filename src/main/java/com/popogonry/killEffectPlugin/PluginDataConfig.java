
package com.popogonry.killEffectPlugin;

public class PluginDataConfig extends Config {
    public PluginDataConfig(String basePath, String fileName) {
        super(basePath, fileName);
    }

    public PluginConfig loadPluginDataConfig() {
        return new PluginConfig(
                ChatColorUtil.translateRGBColors(this.getConfig().getString("Lore-Display-Text")),
                ChatColorUtil.translateRGBColors(this.getConfig().getString("Cooldown-Display-Text")),
                ChatColorUtil.translateRGBColors(this.getConfig().getString("ActiveType-Display-Text")),
                this.getConfig().getString("KillEffect-GUI-Name"),
                this.getConfig().getString("KillEffect-Control-GUI-Name"),
                this.getConfig().getString("KillEffect-GUI-LeftPage-Item"),
                this.getConfig().getString("KillEffect-GUI-RightPage-Item"),
                this.getConfig().getString("KillEffect-GUI-Information-Item")
        );
    }

    public void loadDefaults() {
    }

    public void applySettings() {
        this.getConfig().options().copyDefaults(true);
    }
}
