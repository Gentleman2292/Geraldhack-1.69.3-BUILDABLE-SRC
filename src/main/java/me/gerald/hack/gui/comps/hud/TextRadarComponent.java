/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.MathHelper
 */
package me.gerald.hack.gui.comps.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Objects;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.HUDComponent;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.module.modules.hud.TextRadar;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class TextRadarComponent
extends HUDComponent {
    public TextRadarComponent(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null) {
            this.updateDragPosition(mouseX, mouseY);
            super.drawScreen(mouseX, mouseY, partialTicks);
            this.width = 75;
            this.height = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
            int yOffset = this.y;
            for (EntityPlayer entity : Minecraft.getMinecraft().world.playerEntities) {
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((Object)ChatFormatting.GRAY + "[" + (Object)this.getHealthColor(entity) + MathHelper.ceil((float)(entity.getHealth() + entity.getAbsorptionAmount())) + (Object)ChatFormatting.GRAY + "] " + (Object)ChatFormatting.RESET + entity.getDisplayNameString() + (Object)ChatFormatting.GRAY + " [" + (Object)this.getPingColor(entity) + MathHelper.ceil((float)Objects.requireNonNull(Minecraft.getMinecraft().getConnection()).getPlayerInfo(entity.getUniqueID()).getResponseTime()) + (Object)ChatFormatting.GRAY + "]", (float)this.x, (float)yOffset, new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
                yOffset += Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
            }
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
        GeraldHack.INSTANCE.moduleManager.getModule(TextRadar.class).x.setValue(this.x);
        GeraldHack.INSTANCE.moduleManager.getModule(TextRadar.class).y.setValue(this.y);
        this.stopDragging();
    }

    @Override
    public void keyTyped(char keyChar, int key) {
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public ChatFormatting getHealthColor(EntityPlayer entity) {
        if (entity.getHealth() + entity.getAbsorptionAmount() > 21.0f) {
            return ChatFormatting.GOLD;
        }
        if (entity.getHealth() + entity.getAbsorptionAmount() > 14.0f) {
            return ChatFormatting.GREEN;
        }
        if (entity.getHealth() + entity.getAbsorptionAmount() > 6.0f) {
            return ChatFormatting.YELLOW;
        }
        return ChatFormatting.RED;
    }

    public ChatFormatting getPingColor(EntityPlayer entity) {
        if (Objects.requireNonNull(Minecraft.getMinecraft().getConnection()).getPlayerInfo(entity.getUniqueID()).getResponseTime() > 150) {
            return ChatFormatting.RED;
        }
        if (Minecraft.getMinecraft().getConnection().getPlayerInfo(entity.getUniqueID()).getResponseTime() > 75) {
            return ChatFormatting.YELLOW;
        }
        return ChatFormatting.GREEN;
    }
}

