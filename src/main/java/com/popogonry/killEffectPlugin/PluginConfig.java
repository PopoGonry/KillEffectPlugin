
package com.popogonry.killEffectPlugin;

public class PluginConfig {
    private final String loreDisPlayerText;
    private final String cooldownDisplayText;
    private final String activeTypeDisplayText;
    private final String killEffectGUIName;
    private final String killEffectControlGUIName;
    private final String killEffectGUILeftPageItem;
    private final String killEffectGUIRightPageItem;
    private final String killEffectGUIInformationItem;

    public PluginConfig(String loreDisPlayerText, String cooldownDisplayText, String activeTypeDisplayText, String killEffectGUIName, String killEffectControlGUIName, String killEffectGUILeftPageItem, String killEffectGUIRightPageItem, String killEffectGUIInfomationItem) {
        this.loreDisPlayerText = loreDisPlayerText;
        this.cooldownDisplayText = cooldownDisplayText;
        this.activeTypeDisplayText = activeTypeDisplayText;
        this.killEffectGUIName = killEffectGUIName;
        this.killEffectControlGUIName = killEffectControlGUIName;
        this.killEffectGUILeftPageItem = killEffectGUILeftPageItem;
        this.killEffectGUIRightPageItem = killEffectGUIRightPageItem;
        this.killEffectGUIInformationItem = killEffectGUIInfomationItem;
    }

    public String getLoreDisPlayerText() {
        return loreDisPlayerText;
    }

    public String getCooldownDisplayText() {
        return cooldownDisplayText;
    }

    public String getActiveTypeDisplayText() {
        return activeTypeDisplayText;
    }

    public String getKillEffectGUIName() {
        return killEffectGUIName;
    }

    public String getKillEffectControlGUIName() {
        return killEffectControlGUIName;
    }

    public String getKillEffectGUILeftPageItem() {
        return killEffectGUILeftPageItem;
    }

    public String getKillEffectGUIRightPageItem() {
        return killEffectGUIRightPageItem;
    }

    public String getKillEffectGUIInformationItem() {
        return killEffectGUIInformationItem;
    }

    @Override
    public String toString() {
        return "PluginConfig{" +
                "loreDisPlayerText='" + loreDisPlayerText + '\'' +
                ", cooldownDisplayText='" + cooldownDisplayText + '\'' +
                ", activeTypeDisplayText='" + activeTypeDisplayText + '\'' +
                ", killEffectGUIName='" + killEffectGUIName + '\'' +
                ", killEffectControlGUIName='" + killEffectControlGUIName + '\'' +
                ", killEffectGUILeftPageItem='" + killEffectGUILeftPageItem + '\'' +
                ", killEffectGUIRightPageItem='" + killEffectGUIRightPageItem + '\'' +
                ", killEffectGUIInformationItem='" + killEffectGUIInformationItem + '\'' +
                '}';
    }
}
