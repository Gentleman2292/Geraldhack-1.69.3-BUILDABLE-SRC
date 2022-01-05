/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Items
 */
package me.gerald.hack.gui.comps.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.HUDComponent;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.module.modules.hud.TotemCount;
import me.gerald.hack.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;

public class TotemComponent
extends HUDComponent {
    public TotemComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.updateDragPosition(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.width = Minecraft.getMinecraft().fontRenderer.getStringWidth("Totems: " + InventoryUtil.getTotalAmountOfItem(Items.TOTEM_OF_UNDYING));
        this.height = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Totems: " + (Object)ChatFormatting.GRAY + InventoryUtil.getTotalAmountOfItem(Items.TOTEM_OF_UNDYING), (float)this.x, (float)this.y, new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isInside(mouseX, mouseY) && mouseButton == 0) {
            this.beginDragging(mouseX, mouseY);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        GeraldHack.INSTANCE.moduleManager.getModule(TotemCount.class).x.setValue(this.x);
        GeraldHack.INSTANCE.moduleManager.getModule(TotemCount.class).y.setValue(this.y);
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

