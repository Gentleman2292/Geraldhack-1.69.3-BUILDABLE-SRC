/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.hud;

import me.gerald.hack.gui.comps.hud.ArrayListComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;

public class ArrayList
extends HUDModule {
    public NumSetting x;
    public NumSetting y;

    public ArrayList() {
        super(new ArrayListComponent(1, 70, 1, 1), "ArrayList", Module.Category.HUD, "Renders enabled modules.");
        this.x = this.register(new NumSetting("X", 1.0f, 0.0f, ArrayList.mc.displayWidth));
        this.y = this.register(new NumSetting("Y", 1.0f, 0.0f, ArrayList.mc.displayHeight));
    }
}

