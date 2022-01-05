/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 */
package me.gerald.hack.gui.comps;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.AbstractContainer;
import me.gerald.hack.gui.api.SettingComponent;
import me.gerald.hack.gui.comps.CategoryComponent;
import me.gerald.hack.gui.comps.settings.BindComponent;
import me.gerald.hack.gui.comps.settings.BoolComponent;
import me.gerald.hack.gui.comps.settings.ColorComponent;
import me.gerald.hack.gui.comps.settings.ModeComponent;
import me.gerald.hack.gui.comps.settings.NumComponent;
import me.gerald.hack.gui.comps.settings.StringComponent;
import me.gerald.hack.module.Module;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.setting.Setting;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.ModeSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.setting.settings.StringSetting;
import me.gerald.hack.util.MessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModuleComponent
extends AbstractContainer {
    public Module module;
    public boolean open;
    public CategoryComponent parent;
    public List<SettingComponent> settingComponents;

    public ModuleComponent(Module module, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.settingComponents = new ArrayList<SettingComponent>();
        module.setParent(this);
        if (module.needsKeybind()) {
            this.settingComponents.add(new BindComponent(module, x, y, width, height));
        }
        for (Setting setting : module.getSettings()) {
            if (setting instanceof BoolSetting) {
                this.settingComponents.add(new BoolComponent(module, (BoolSetting)setting, x, y, width, height));
                continue;
            }
            if (setting instanceof NumSetting) {
                this.settingComponents.add(new NumComponent(module, (NumSetting)setting, x, y, width, height));
                continue;
            }
            if (setting instanceof ColorSetting) {
                this.settingComponents.add(new ColorComponent(module, (ColorSetting)setting, x, y, width, height));
                continue;
            }
            if (setting instanceof ModeSetting) {
                this.settingComponents.add(new ModeComponent(module, (ModeSetting)setting, x, y, width, height));
                continue;
            }
            if (!(setting instanceof StringSetting)) continue;
            this.settingComponents.add(new StringComponent(module, (StringSetting)setting, x, y, width, height));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int yOffset = this.height;
        Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height + 1), (int)new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
        Gui.drawRect((int)(this.x + 1), (int)this.y, (int)(this.x + this.width - 1), (int)(this.y + this.height), (int)(this.module.isEnabled() ? new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getA()).getRGB() : (this.isInside(mouseX, mouseY) ? new Color(30, 30, 30, 240).getRGB() : new Color(15, 15, 15, 240).getRGB())));
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.module.getName(), (float)(this.x + 2), (float)(this.y + 2), this.module.isEnabled() ? -1 : (this.isInside(mouseX, mouseY) ? new Color(160, 160, 160).getRGB() : new Color(130, 130, 130).getRGB()));
        if (this.module.getCategory() != Module.Category.DESCRIPTION) {
            if (this.open && this.module.hasSetting() || this.open && this.module.needsKeybind()) {
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("-", (float)(this.x + this.width - Minecraft.getMinecraft().fontRenderer.getStringWidth("-") - 1), (float)(this.y + 2), this.module.isEnabled() ? -1 : new Color(130, 130, 130).getRGB());
            } else if (!this.open && this.module.hasSetting() || !this.open && this.module.needsKeybind()) {
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("+", (float)(this.x + this.width - Minecraft.getMinecraft().fontRenderer.getStringWidth("-") - 1), (float)(this.y + 2), this.module.isEnabled() ? -1 : new Color(130, 130, 130).getRGB());
            }
        }
        if (this.open) {
            for (SettingComponent component : this.settingComponents) {
                component.x = this.x;
                component.y = this.y + yOffset;
                yOffset += component.getHeight();
                component.drawScreen(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.open) {
            for (SettingComponent component : this.settingComponents) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        if (this.isInside(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.module.toggle();
            } else if (mouseButton == 1) {
                this.open = !this.open;
            } else if (mouseButton == 2) {
                this.module.isVisible = !this.module.isVisible;
                MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + this.module.getName() + "'s " + (Object)ChatFormatting.RESET + " visibility has been set to " + (this.module.isVisible ? (Object)ChatFormatting.GREEN + "TRUE" : (Object)ChatFormatting.RED + "FALSE"));
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (this.open) {
            for (SettingComponent component : this.settingComponents) {
                component.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
        GeraldHack.INSTANCE.configManager.saveModule(this.module);
    }

    @Override
    public void keyTyped(char keyChar, int key) throws IOException, UnsupportedFlavorException {
        if (this.open) {
            for (SettingComponent component : this.settingComponents) {
                component.keyTyped(keyChar, key);
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            int h = this.height;
            for (SettingComponent component : this.settingComponents) {
                h += component.getHeight();
            }
            return h;
        }
        return this.height;
    }
}

