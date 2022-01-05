/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.gerald.hack.gui;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.AbstractContainer;
import me.gerald.hack.gui.comps.CategoryComponent;
import me.gerald.hack.module.HUDModule;
import me.gerald.hack.module.Module;
import net.minecraft.client.gui.GuiScreen;

public class HUDEditor
extends GuiScreen {
    public List<AbstractContainer> components = new ArrayList<AbstractContainer>();

    public HUDEditor() {
        this.components.add(new CategoryComponent(Module.Category.HUD, 25, 50, 125, 11));
        for (Module module : GeraldHack.INSTANCE.moduleManager.getModulesByCategory(Module.Category.HUD)) {
            HUDModule hudModule = (HUDModule)module;
            this.components.add(hudModule.getComponent());
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (AbstractContainer component : this.components) {
            component.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (AbstractContainer component : this.components) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for (AbstractContainer component : this.components) {
            component.mouseReleased(mouseX, mouseY, state);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (AbstractContainer component : this.components) {
            try {
                component.keyTyped(typedChar, keyCode);
            }
            catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}

