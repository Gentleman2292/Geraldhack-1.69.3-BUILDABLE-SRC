/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.hud;

import me.gerald.hack.gui.comps.hud.ObbyComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;

public class ObbyCount
extends HUDModule {
    public NumSetting x;
    public NumSetting y;
    public BoolSetting stackCount;

    public ObbyCount() {
        super(new ObbyComponent(1, 40, 1, 1), "ObbyCount", Module.Category.HUD, "Counts how much obsidian you have.");
        this.x = this.register(new NumSetting("X", 1.0f, 0.0f, ObbyCount.mc.displayWidth));
        this.y = this.register(new NumSetting("Y", 1.0f, 0.0f, ObbyCount.mc.displayHeight));
        this.stackCount = this.register(new BoolSetting("StackCount", true));
    }
}

