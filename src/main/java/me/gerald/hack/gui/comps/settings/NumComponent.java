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
import java.math.BigDecimal;
import java.math.RoundingMode;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.SettingComponent;
import me.gerald.hack.module.Module;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.module.modules.client.Description;
import me.gerald.hack.setting.settings.NumSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class NumComponent
extends SettingComponent {
    public NumSetting setting;
    public Module module;
    public float sliderWidth;
    public boolean dragging = false;

    public NumComponent(Module module, NumSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.updateSliderLogic(mouseX);
        Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height + 1), (int)new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
        Gui.drawRect((int)(this.x + 1), (int)this.y, (int)(this.x + this.width - 1), (int)(this.y + this.height), (int)(this.isInside(mouseX, mouseY) ? new Color(30, 30, 30, 240).getRGB() : new Color(15, 15, 15, 240).getRGB()));
        if (GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).thinSliders.getValue()) {
            Gui.drawRect((int)this.x, (int)(this.y + this.height - 1), (int)(this.x + (int)this.sliderWidth), (int)(this.y + this.height - 2), (int)new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
        } else {
            Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + (int)this.sliderWidth), (int)(this.y + this.height), (int)new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
        }
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.setting.getName() + " " + (Object)ChatFormatting.GRAY + this.setting.getValue(), (float)(this.x + 5), (float)(this.y + 2), this.module.isEnabled() ? -1 : (this.isInside(mouseX, mouseY) ? new Color(160, 160, 160).getRGB() : new Color(130, 130, 130).getRGB()));
        if (this.isInside(mouseX, mouseY)) {
            GeraldHack.INSTANCE.moduleManager.getModule(Description.class).setName("A number setting.");
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isInside(mouseX, mouseY) && mouseButton == 0) {
            this.dragging = !this.dragging;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (this.dragging) {
            this.dragging = false;
        }
    }

    @Override
    public void keyTyped(char keyChar, int key) {
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    protected void updateSliderLogic(int mouseX) {
        float diff = Math.min(this.width, Math.max(0, mouseX - this.x));
        float min = this.setting.getMin();
        float max = this.setting.getMax();
        this.sliderWidth = (float)this.width * (this.setting.getValue() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0f) {
                this.setting.setValue(this.setting.getMin());
            } else {
                float value = NumComponent.roundToPlace(diff / (float)this.width * (max - min) + min, 1);
                this.setting.setValue(value);
            }
        }
    }

    public static float roundToPlace(float value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}

