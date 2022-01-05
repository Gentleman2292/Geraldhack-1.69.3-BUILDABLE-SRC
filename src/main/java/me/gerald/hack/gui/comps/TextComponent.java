/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 */
package me.gerald.hack.gui.comps;

import java.awt.Color;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.AbstractContainer;
import me.gerald.hack.gui.api.DragComponent;
import me.gerald.hack.module.modules.client.ClickGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class TextComponent
extends DragComponent {
    public static List<AbstractContainer> components = new ArrayList<AbstractContainer>();
    public String text;

    public TextComponent(String text, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.text = text;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.updateDragPosition(mouseX, mouseY);
        int yOffset = this.height;
        Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), (int)new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.text, (float)(this.x + 2), (float)(this.y + 2), -1);
        for (AbstractContainer component : components) {
            component.x = this.x;
            component.y = this.y + yOffset;
            yOffset += component.getHeight();
            component.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isInside(mouseX, mouseY) && mouseButton == 0) {
            this.beginDragging(mouseX, mouseY);
        }
        for (AbstractContainer component : components) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.stopDragging();
        for (AbstractContainer component : components) {
            component.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char keyChar, int key) throws IOException, UnsupportedFlavorException {
        for (AbstractContainer component : components) {
            component.keyTyped(keyChar, key);
        }
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addComponent(AbstractContainer component) {
        components.add(component);
    }
}

