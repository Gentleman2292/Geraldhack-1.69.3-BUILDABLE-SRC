/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.hud;

import me.gerald.hack.gui.comps.hud.GappleComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;

public class GappleCount
extends HUDModule {
    public NumSetting x;
    public NumSetting y;
    public BoolSetting stackCount;

    public GappleCount() {
        super(new GappleComponent(1, 30, 1, 1), "GappleCount", Module.Category.HUD, "Counts how many gapples you have.");
        this.x = this.register(new NumSetting("X", 1.0f, 0.0f, GappleCount.mc.displayWidth));
        this.y = this.register(new NumSetting("Y", 1.0f, 0.0f, GappleCount.mc.displayHeight));
        this.stackCount = this.register(new BoolSetting("StackCount", true));
    }
}

