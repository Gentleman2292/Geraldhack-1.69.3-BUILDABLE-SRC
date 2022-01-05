/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.math.MathHelper
 */
package me.gerald.hack.gui.comps.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.HUDComponent;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.module.modules.hud.CrystalCount;
import me.gerald.hack.module.modules.hud.ObbyCount;
import me.gerald.hack.util.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

public class ObbyComponent
extends HUDComponent {
    public ObbyComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.updateDragPosition(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.width = Minecraft.getMinecraft().fontRenderer.getStringWidth("Obsidian: " + InventoryUtil.getTotalAmountOfItem(Item.getItemFromBlock((Block)Blocks.OBSIDIAN)) + (GeraldHack.INSTANCE.moduleManager.getModule(CrystalCount.class).stackCount.getValue() ? " [" + (float)InventoryUtil.getTotalAmountOfItem(Item.getItemFromBlock((Block)Blocks.OBSIDIAN)) / 64.0f + "]" : ""));
        this.height = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Obsidian: " + (Object)ChatFormatting.GRAY + InventoryUtil.getTotalAmountOfItem(Item.getItemFromBlock((Block)Blocks.OBSIDIAN)) + (GeraldHack.INSTANCE.moduleManager.getModule(CrystalCount.class).stackCount.getValue() ? " [" + MathHelper.ceil((float)((float)InventoryUtil.getTotalAmountOfItem(Item.getItemFromBlock((Block)Blocks.OBSIDIAN)) / 64.0f)) + "]" : ""), (float)this.x, (float)this.y, new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isInside(mouseX, mouseY) && mouseButton == 0) {
            this.beginDragging(mouseX, mouseY);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        GeraldHack.INSTANCE.moduleManager.getModule(ObbyCount.class).x.setValue(this.x);
        GeraldHack.INSTANCE.moduleManager.getModule(ObbyCount.class).y.setValue(this.y);
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

