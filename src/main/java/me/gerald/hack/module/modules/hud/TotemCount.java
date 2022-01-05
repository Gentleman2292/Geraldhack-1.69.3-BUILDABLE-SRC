/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.hud;

import me.gerald.hack.gui.comps.hud.TotemComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;

public class TotemCount
extends HUDModule {
    public NumSetting x;
    public NumSetting y;

    public TotemCount() {
        super(new TotemComponent(1, 50, 1, 1), "TotemCount", Module.Category.HUD, "Counts all the totems you have.");
        this.x = this.register(new NumSetting("X", 1.0f, 0.0f, TotemCount.mc.displayWidth));
        this.y = this.register(new NumSetting("Y", 1.0f, 0.0f, TotemCount.mc.displayHeight));
    }
}

