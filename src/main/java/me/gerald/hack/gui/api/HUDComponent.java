/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 */
package me.gerald.hack.gui.api;

import me.gerald.hack.gui.HUDEditor;
import me.gerald.hack.gui.api.DragComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public abstract class HUDComponent
extends DragComponent {
    public HUDComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Minecraft.getMinecraft().currentScreen instanceof HUDEditor) {
            Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), (int)(this.isInside(mouseX, mouseY) ? 0x50FFFFFF : -1879048192));
        }
    }
}

