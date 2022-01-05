/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 */
package me.gerald.hack.gui.comps.settings;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.ClickGUI;
import me.gerald.hack.gui.api.SettingComponent;
import me.gerald.hack.gui.comps.settings.BoolComponent;
import me.gerald.hack.gui.comps.settings.NumComponent;
import me.gerald.hack.module.Module;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.module.modules.client.Description;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.MessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ColorComponent
extends SettingComponent {
    public boolean open = false;
    public ColorSetting setting;
    public Module module;
    public List<NumComponent> components;
    public List<BoolComponent> boolComponents;

    public ColorComponent(Module module, ColorSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.components = new ArrayList<NumComponent>();
        this.components.add(new NumComponent(module, new NumSetting("Red", setting.getR(), 0.0f, 255.0f), x, y, width, height));
        this.components.add(new NumComponent(module, new NumSetting("Green", setting.getG(), 0.0f, 255.0f), x, y, width, height));
        this.components.add(new NumComponent(module, new NumSetting("Blue", setting.getB(), 0.0f, 255.0f), x, y, width, height));
        this.components.add(new NumComponent(module, new NumSetting("Alpha", setting.getA(), 0.0f, 255.0f), x, y, width, height));
        this.boolComponents = new ArrayList<BoolComponent>();
        this.boolComponents.add(new BoolComponent(module, new BoolSetting("Copy", false), x, y, width, height));
        this.boolComponents.add(new BoolComponent(module, new BoolSetting("Paste", false), x, y, width, height));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height + 1), (int)new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
        Gui.drawRect((int)(this.x + 1), (int)this.y, (int)(this.x + this.width - 1), (int)(this.y + this.height), (int)(this.isInside(mouseX, mouseY) ? new Color(30, 30, 30, 240).getRGB() : new Color(15, 15, 15, 240).getRGB()));
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.setting.getName(), (float)(this.x + 5), (float)(this.y + 2), this.module.isEnabled() ? -1 : (this.isInside(mouseX, mouseY) ? new Color(160, 160, 160).getRGB() : new Color(130, 130, 130).getRGB()));
        Gui.drawRect((int)(this.x + this.width - 12), (int)(this.y + 1), (int)(this.x + this.width - 2), (int)(this.y + this.height - 1), (int)new Color((int)this.setting.getR(), (int)this.setting.getG(), (int)this.setting.getB(), 255).getRGB());
        if (this.open) {
            int yOffset = this.height;
            for (SettingComponent settingComponent : this.components) {
                settingComponent.x = this.x;
                settingComponent.y = this.y + yOffset;
                yOffset += settingComponent.getHeight();
                settingComponent.drawScreen(mouseX, mouseY, partialTicks);
            }
            for (SettingComponent settingComponent : this.boolComponents) {
                settingComponent.x = this.x;
                settingComponent.y = this.y + yOffset;
                yOffset += settingComponent.getHeight();
                settingComponent.drawScreen(mouseX, mouseY, partialTicks);
            }
            this.setting.setR(this.components.get((int)0).setting.getValue());
            this.setting.setG(this.components.get((int)1).setting.getValue());
            this.setting.setB(this.components.get((int)2).setting.getValue());
            this.setting.setA(this.components.get((int)3).setting.getValue());
        }
        if (this.isInside(mouseX, mouseY)) {
            GeraldHack.INSTANCE.moduleManager.getModule(Description.class).setName("A color setting.");
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isInside(mouseX, mouseY) && mouseButton == 1) {
            boolean bl = this.open = !this.open;
        }
        if (this.open) {
            for (SettingComponent settingComponent : this.components) {
                settingComponent.mouseClicked(mouseX, mouseY, mouseButton);
            }
            for (SettingComponent settingComponent : this.boolComponents) {
                settingComponent.mouseClicked(mouseX, mouseY, mouseButton);
            }
            if (this.boolComponents.get((int)0).setting.getValue()) {
                ClickGUI.copiedColor = new Color((int)this.components.get((int)0).setting.getValue(), (int)this.components.get((int)1).setting.getValue(), (int)this.components.get((int)2).setting.getValue(), (int)this.components.get((int)3).setting.getValue());
                MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Copied color [" + (Object)ChatFormatting.GREEN + this.components.get((int)0).setting.getValue() + ", " + this.components.get((int)1).setting.getValue() + ", " + this.components.get((int)2).setting.getValue() + ", " + this.components.get((int)3).setting.getValue() + (Object)ChatFormatting.GRAY + "]");
                this.boolComponents.get((int)0).setting.cycle();
            }
            if (this.boolComponents.get((int)1).setting.getValue()) {
                if (ClickGUI.copiedColor == null) {
                    MessageUtil.sendErrorMessage("You have no color copied.");
                    this.boolComponents.get((int)1).setting.cycle();
                    return;
                }
                this.components.get((int)0).setting.setValue(ClickGUI.copiedColor.getRed());
                this.components.get((int)1).setting.setValue(ClickGUI.copiedColor.getGreen());
                this.components.get((int)2).setting.setValue(ClickGUI.copiedColor.getBlue());
                this.components.get((int)3).setting.setValue(ClickGUI.copiedColor.getAlpha());
                MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Pasted color [" + (Object)ChatFormatting.GREEN + this.components.get((int)0).setting.getValue() + ", " + this.components.get((int)1).setting.getValue() + ", " + this.components.get((int)2).setting.getValue() + ", " + this.components.get((int)3).setting.getValue() + (Object)ChatFormatting.GRAY + "]");
                this.boolComponents.get((int)1).setting.cycle();
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (this.open) {
            for (SettingComponent settingComponent : this.components) {
                settingComponent.mouseReleased(mouseX, mouseY, mouseButton);
            }
            for (SettingComponent settingComponent : this.boolComponents) {
                settingComponent.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void keyTyped(char keyChar, int key) throws IOException, UnsupportedFlavorException {
        if (this.open) {
            for (SettingComponent settingComponent : this.components) {
                settingComponent.keyTyped(keyChar, key);
            }
            for (SettingComponent settingComponent : this.boolComponents) {
                settingComponent.keyTyped(keyChar, key);
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            int h = this.height;
            for (SettingComponent settingComponent : this.components) {
                h += settingComponent.getHeight();
            }
            for (SettingComponent settingComponent : this.boolComponents) {
                h += settingComponent.getHeight();
            }
            return h;
        }
        return this.height;
    }
}

