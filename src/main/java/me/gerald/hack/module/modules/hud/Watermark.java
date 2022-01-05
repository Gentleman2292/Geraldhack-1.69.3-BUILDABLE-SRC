/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.hud;

import me.gerald.hack.gui.comps.hud.WatermarkComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.setting.settings.StringSetting;

public class Watermark
extends HUDModule {
    public NumSetting x;
    public NumSetting y;
    public StringSetting clientName;
    public BoolSetting version;
    public StringSetting versionString;

    public Watermark() {
        super(new WatermarkComponent(1, 1, 1, 1), "Watermark", Module.Category.HUD, "Renders the watermark.");
        this.x = this.register(new NumSetting("X", 1.0f, 0.0f, Watermark.mc.displayWidth));
        this.y = this.register(new NumSetting("Y", 1.0f, 0.0f, Watermark.mc.displayHeight));
        this.clientName = this.register(new StringSetting("ClientName", "Gerald(Hack)"));
        this.version = this.register(new BoolSetting("Version", true));
        this.versionString = this.register(new StringSetting("Version", "1.69.3"));
    }
}

