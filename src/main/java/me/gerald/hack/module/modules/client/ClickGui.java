/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.gerald.hack.module.modules.client;

import me.gerald.hack.GeraldHack;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.ModeSetting;
import net.minecraft.client.gui.GuiScreen;

public class ClickGui
extends Module {
    public ColorSetting color = this.register(new ColorSetting("Color", 0.0f, 125.0f, 255.0f, 0.0f));
    public BoolSetting thinSliders = this.register(new BoolSetting("ThinSliders", true));
    public ModeSetting descriptionMode = this.register(new ModeSetting("DescriptionMode", DescriptionMode.Category));

    public ClickGui() {
        super("ClickGui", Module.Category.CLIENT, "Renders client clickGUI.");
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen((GuiScreen)GeraldHack.INSTANCE.gui);
        this.toggle();
    }

    public static enum DescriptionMode {
        Category,
        Text;

    }
}

