package com.popogonry.killEffectPlugin.killEffect;

public class KillEffect {
    private String name;
    private String mysticmobName;
    private String lore;
    private double cooldown;
    private KillEffectActiveType activeType;

    public KillEffect(String name) {
        this.name = name;
    }

    public KillEffect(String name, String mysticmobName, String lore, double cooldown, KillEffectActiveType activeType) {
        this.name = name;
        this.mysticmobName = mysticmobName;
        this.lore = lore;
        this.cooldown = cooldown;
        this.activeType = activeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMysticmobName() {
        return mysticmobName;
    }

    public void setMysticmobName(String mysticmobName) {
        this.mysticmobName = mysticmobName;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public KillEffectActiveType getActiveType() {
        return activeType;
    }

    public void setActiveType(KillEffectActiveType activeType) {
        this.activeType = activeType;
    }

    @Override
    public String toString() {
        return "KillEffect{" +
                "name='" + name + '\'' +
                ", mysticmobName='" + mysticmobName + '\'' +
                ", lore='" + lore + '\'' +
                ", cooldown=" + cooldown +
                ", activeType=" + activeType +
                '}';
    }
}
