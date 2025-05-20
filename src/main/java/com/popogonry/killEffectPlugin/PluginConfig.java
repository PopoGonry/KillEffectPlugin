
package com.popogonry.killEffectPlugin;

public class PluginConfig {
    private final String loreDisPlayerText;
    private final String cooldownDisplayText;
    private final String activeTypeDisplayText;
    private final String killEffectGUIName;
    private final String killEffectControlGUIName;

    public PluginConfig(String loreDisPlayerText, String cooldownDisplayText, String activeTypeDisplayText, String killEffectGUIName, String killEffectControlGUIName) {
        this.loreDisPlayerText = loreDisPlayerText;
        this.cooldownDisplayText = cooldownDisplayText;
        this.activeTypeDisplayText = activeTypeDisplayText;
        this.killEffectGUIName = killEffectGUIName;
        this.killEffectControlGUIName = killEffectControlGUIName;
    }

    public String getLoreDisPlayerText() {
        return this.loreDisPlayerText;
    }

    public String getCooldownDisplayText() {
        return this.cooldownDisplayText;
    }

    public String getActiveTypeDisplayText() {
        return this.activeTypeDisplayText;
    }

    public String getKillEffectGUIName() {
        return this.killEffectGUIName;
    }

    public String getKillEffectControlGUIName() {
        return this.killEffectControlGUIName;
    }

    public String toString() {
        return "PluginConfig{loreDisPlayerText='" + this.loreDisPlayerText + "', cooldownDisplayText='" + this.cooldownDisplayText + "', activeTypeDisplayText='" + this.activeTypeDisplayText + "', killEffectGUIName='" + this.killEffectGUIName + "', killEffectControlGUIName='" + this.killEffectControlGUIName + "'}";
    }
}
