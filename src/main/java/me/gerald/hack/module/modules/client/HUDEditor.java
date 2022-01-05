/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.gerald.hack.module.modules.client;

import me.gerald.hack.module.Module;
import net.minecraft.client.gui.GuiScreen;

public class HUDEditor
extends Module {
    public HUDEditor() {
        super("HUDEditor", Module.Category.CLIENT, "HUDEditor for the client.");
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen((GuiScreen)new me.gerald.hack.gui.HUDEditor());
        this.toggle();
    }
}

