/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.hud;

import me.gerald.hack.gui.comps.hud.XPComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;

public class XPCount
extends HUDModule {
    public NumSetting x;
    public NumSetting y;
    public BoolSetting stackCount;

    public XPCount() {
        super(new XPComponent(1, 60, 1, 1), "XPCount", Module.Category.HUD, "Counts experience bottles in inventory.");
        this.x = this.register(new NumSetting("X", 1.0f, 0.0f, XPCount.mc.displayWidth));
        this.y = this.register(new NumSetting("Y", 1.0f, 0.0f, XPCount.mc.displayHeight));
        this.stackCount = this.register(new BoolSetting("StackCount", true));
    }
}

