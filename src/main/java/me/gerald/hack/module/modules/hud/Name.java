/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.hud;

import me.gerald.hack.gui.comps.hud.NameComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;

public class Name
extends HUDModule {
    public NumSetting x;
    public NumSetting y;

    public Name() {
        super(new NameComponent(1, 10, 1, 1), "Name", Module.Category.HUD, "Renders player name.");
        this.x = this.register(new NumSetting("X", 1.0f, 0.0f, Name.mc.displayWidth));
        this.y = this.register(new NumSetting("Y", 1.0f, 0.0f, Name.mc.displayHeight));
    }
}

