/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.util;

public class NumberUtil {
    public float value;
    public float defaultValue;

    public NumberUtil(int value) {
        this.value = this.defaultValue = (float)value;
    }

    public NumberUtil(float value) {
        this.value = this.defaultValue = value;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void increase(float count) {
        int i = 0;
        while ((float)i < count) {
            this.value += 1.0f;
            ++i;
        }
    }

    public void decrease(float count) {
        int i = 0;
        while ((float)i < count) {
            this.value -= 1.0f;
            ++i;
        }
    }

    public void reset() {
        this.value = this.defaultValue;
    }

    public void zero() {
        this.value = 0.0f;
    }
}

