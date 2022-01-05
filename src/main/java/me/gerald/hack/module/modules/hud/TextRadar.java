/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.hud;

import me.gerald.hack.gui.comps.hud.TextRadarComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;

public class TextRadar
extends HUDModule {
    public NumSetting x;
    public NumSetting y;

    public TextRadar() {
        super(new TextRadarComponent(75, 1, 1, 1), "TextRadar", Module.Category.HUD, "Sheesh.");
        this.x = this.register(new NumSetting("X", 1.0f, 0.0f, TextRadar.mc.displayWidth));
        this.y = this.register(new NumSetting("Y", 1.0f, 0.0f, TextRadar.mc.displayHeight));
    }
}

