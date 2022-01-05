/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.gerald.hack.gui.comps.hud;

import java.awt.Color;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.HUDComponent;
import me.gerald.hack.module.Module;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.module.modules.hud.ArrayList;
import net.minecraft.client.Minecraft;

public class ArrayListComponent
extends HUDComponent {
    public ArrayListComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.updateDragPosition(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.width = 75;
        this.height = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        int yOffset = this.y;
        for (Module module : GeraldHack.INSTANCE.moduleManager.getSortedModules()) {
            if (!module.isEnabled() || !module.isVisible) continue;
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(module.getName() + " " + module.getMetaData(), (float)this.x, (float)yOffset, new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
            yOffset += Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isInside(mouseX, mouseY) && mouseButton == 0) {
            this.beginDragging(mouseX, mouseY);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        GeraldHack.INSTANCE.moduleManager.getModule(ArrayList.class).x.setValue(this.x);
        GeraldHack.INSTANCE.moduleManager.getModule(ArrayList.class).y.setValue(this.y);
        this.stopDragging();
    }

    @Override
    public void keyTyped(char keyChar, int key) {
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}

