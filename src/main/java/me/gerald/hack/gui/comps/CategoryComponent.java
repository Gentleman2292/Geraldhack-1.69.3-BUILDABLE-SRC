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
import me.gerald.hack.gui.api.DragComponent;
import me.gerald.hack.gui.comps.ModuleComponent;
import me.gerald.hack.module.Module;
import me.gerald.hack.module.modules.client.ClickGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class CategoryComponent
extends DragComponent {
    public Module.Category category;
    public List<ModuleComponent> components = new ArrayList<ModuleComponent>();
    int yOffset = this.height;

    public CategoryComponent(Module.Category category, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        for (Module module : GeraldHack.INSTANCE.moduleManager.getModulesByCategory(category)) {
            this.components.add(new ModuleComponent(module, x, y + this.yOffset, width, height));
            for (ModuleComponent component : this.components) {
                if (component.module != module) continue;
                component.parent = this;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.updateDragPosition(mouseX, mouseY);
        int yOffset = this.height;
        Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), (int)new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.category.name(), (float)(this.x + 2), (float)(this.y + 2), -1);
        for (ModuleComponent component : this.components) {
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
        for (ModuleComponent component : this.components) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.stopDragging();
        for (ModuleComponent component : this.components) {
            component.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char keyChar, int key) throws IOException, UnsupportedFlavorException {
        for (ModuleComponent component : this.components) {
            component.keyTyped(keyChar, key);
        }
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}

