/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.setting.settings;

import me.gerald.hack.setting.Setting;

public class NumSetting
extends Setting {
    private float value;
    private final float min;
    private final float max;

    public NumSetting(String name, float value, float min, float max) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public float getValue() {
        return this.value;
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }

    public void setValue(float value) {
        this.value = value;
    }
}

