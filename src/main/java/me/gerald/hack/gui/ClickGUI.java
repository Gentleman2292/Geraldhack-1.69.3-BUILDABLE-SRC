/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package me.gerald.hack.gui;

import java.awt.Color;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.AbstractContainer;
import me.gerald.hack.gui.comps.CategoryComponent;
import me.gerald.hack.gui.comps.ModuleComponent;
import me.gerald.hack.module.Module;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.module.modules.client.Description;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI
extends GuiScreen {
    public static List<CategoryComponent> components = new ArrayList<CategoryComponent>();
    public static Color copiedColor;

    public ClickGUI() {
        int offset = 25;
        components.add(new CategoryComponent(Module.Category.DESCRIPTION, 25, 25, 125, 11));
        for (Module.Category category : Module.Category.values()) {
            if (category == Module.Category.HUD || category == Module.Category.DESCRIPTION) {
                return;
            }
            components.add(new CategoryComponent(category, offset, 50, 125, 11));
            offset += 130;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (AbstractContainer abstractContainer : components) {
            abstractContainer.drawScreen(mouseX, mouseY, partialTicks);
        }
        for (CategoryComponent categoryComponent : components) {
            if (categoryComponent.category == Module.Category.DESCRIPTION) continue;
            for (ModuleComponent moduleComponent : categoryComponent.components) {
                Module module = moduleComponent.module;
                if (!moduleComponent.isInside(mouseX, mouseY)) continue;
                GeraldHack.INSTANCE.moduleManager.getModule(Description.class).setName(module.getDescription());
                if (GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).descriptionMode.getValueEnum() != ClickGui.DescriptionMode.Text) continue;
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(module.getDescription(), (float)(mouseX + 2), (float)(mouseY - 4), -1);
            }
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (AbstractContainer abstractContainer : components) {
            abstractContainer.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (AbstractContainer abstractContainer : components) {
            abstractContainer.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (AbstractContainer abstractContainer : components) {
            try {
                abstractContainer.keyTyped(typedChar, keyCode);
            }
            catch (UnsupportedFlavorException unsupportedFlavorException) {}
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}

