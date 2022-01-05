/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.setting.settings;

import me.gerald.hack.setting.Setting;

public class ModeSetting
extends Setting {
    private final Enum<?>[] constants;
    private int modeIndex;
    private final int defaultValueIndex;

    public ModeSetting(String name, Enum<?> defaultValue) {
        super(name);
        this.constants = (Enum[])defaultValue.getDeclaringClass().getEnumConstants();
        this.defaultValueIndex = this.modeIndex = this.indexOf(defaultValue);
    }

    public void increase() {
        this.modeIndex = this.modeIndex == this.constants.length - 1 ? 0 : ++this.modeIndex;
    }

    public void decrease() {
        this.modeIndex = this.modeIndex == 0 ? this.constants.length - 1 : --this.modeIndex;
    }

    public Enum<?> getValueEnum() {
        return this.constants[this.modeIndex];
    }

    public int getValueIndex() {
        return this.modeIndex;
    }

    public void setValueIndex(int value) {
        this.modeIndex = value;
    }

    public int getDefaultValueIndex() {
        return this.defaultValueIndex;
    }

    private int indexOf(Enum<?> value) {
        for (int i = 0; i < this.constants.length; ++i) {
            if (this.constants[i] != value) continue;
            return i;
        }
        return -1;
    }
}

