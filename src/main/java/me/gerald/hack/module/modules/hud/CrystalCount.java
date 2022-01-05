/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.hud;

import me.gerald.hack.gui.comps.hud.CrystalComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;

public class CrystalCount
extends HUDModule {
    public NumSetting x;
    public NumSetting y;
    public BoolSetting stackCount;

    public CrystalCount() {
        super(new CrystalComponent(1, 20, 1, 1), "CrystalCount", Module.Category.HUD, "Renders how many crystals you have.");
        this.x = this.register(new NumSetting("X", 1.0f, 0.0f, CrystalCount.mc.displayWidth));
        this.y = this.register(new NumSetting("Y", 1.0f, 0.0f, CrystalCount.mc.displayHeight));
        this.stackCount = this.register(new BoolSetting("StackCount", true));
    }
}

