
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
                ChatColorUtil.translateRGBColors(this.getConfig().getString("KillEffect-GUI-Name")),
                ChatColorUtil.translateRGBColors(this.getConfig().getString("KillEffect-Control-GUI-Name")),
                this.getConfig().getString("KillEffect-GUI-Left-Page-Item"),
                this.getConfig().getString("KillEffect-GUI-Right-Page-Item"),
                this.getConfig().getString("KillEffect-GUI-Information-Item")
        );
    }

    public void loadDefaults() {
    }

    public void applySettings() {
        this.getConfig().options().copyDefaults(true);
    }
}
