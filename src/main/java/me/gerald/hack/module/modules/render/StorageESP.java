/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityEnderChest
 *  net.minecraft.tileentity.TileEntityHopper
 *  net.minecraft.tileentity.TileEntityShulkerBox
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.gerald.hack.module.modules.render;

import java.awt.Color;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.ModeSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StorageESP
extends Module {
    public ModeSetting renderMode = this.register(new ModeSetting("RenderMode", RenderMode.Both));
    public ModeSetting colorMode = this.register(new ModeSetting("ColorMode", ColorMode.Static));
    public ColorSetting color = this.register(new ColorSetting("Color", 0.0f, 125.0f, 255.0f, 43.0f));
    public BoolSetting rainbow = this.register(new BoolSetting("Rainbow", false));
    public NumSetting rainbowSpeed = this.register(new NumSetting("RainbowSpeed", 6.0f, 1.0f, 10.0f));
    public NumSetting lineWidth = this.register(new NumSetting("LineWidth", 2.0f, 0.0f, 4.0f));
    public BoolSetting chest = this.register(new BoolSetting("Chests", true));
    public BoolSetting enderChest = this.register(new BoolSetting("EnderChests", true));
    public BoolSetting shulker = this.register(new BoolSetting("Shulkers", true));
    public BoolSetting hopper = this.register(new BoolSetting("Hoppers", true));

    public StorageESP() {
        super("StorageESP", Module.Category.RENDER, "Renders boxes around storage.");
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        for (TileEntity entity : StorageESP.mc.world.loadedTileEntityList) {
            RenderUtil.prepare();
            GlStateManager.glLineWidth((float)this.lineWidth.getValue());
            if (entity instanceof TileEntityChest && this.chest.getValue()) {
                this.render(entity, new Color(100, 58, 11));
            }
            if (entity instanceof TileEntityEnderChest && this.enderChest.getValue()) {
                this.render(entity, new Color(113, 37, 217));
            }
            if (entity instanceof TileEntityHopper && this.hopper.getValue()) {
                this.render(entity, new Color(72, 72, 72));
            }
            if (entity instanceof TileEntityShulkerBox && this.shulker.getValue()) {
                this.render(entity, new Color(227, 19, 182));
            }
            RenderUtil.release();
        }
    }

    public void render(TileEntity entity, Color staticColor) {
        AxisAlignedBB box = new AxisAlignedBB(entity.getPos());
        box = box.offset(-StorageESP.mc.getRenderManager().viewerPosX, -StorageESP.mc.getRenderManager().viewerPosY, -StorageESP.mc.getRenderManager().viewerPosZ);
        float r = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getRed() : (this.colorMode.getValueEnum() == ColorMode.Custom ? this.color.getR() : (float)staticColor.getRed())) / 255.0f;
        float g = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getGreen() : (this.colorMode.getValueEnum() == ColorMode.Custom ? this.color.getG() : (float)staticColor.getGreen())) / 255.0f;
        float b = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getBlue() : (this.colorMode.getValueEnum() == ColorMode.Custom ? this.color.getB() : (float)staticColor.getBlue())) / 255.0f;
        if (this.renderMode.getValueEnum() == RenderMode.Fill || this.renderMode.getValueEnum() == RenderMode.Both) {
            RenderGlobal.renderFilledBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)r, (float)g, (float)b, (float)(this.color.getA() / 255.0f));
        }
        if (this.renderMode.getValueEnum() == RenderMode.Outline || this.renderMode.getValueEnum() == RenderMode.Both) {
            RenderGlobal.drawBoundingBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)r, (float)g, (float)b, (float)1.0f);
        }
    }

    public static enum ColorMode {
        Static,
        Custom;

    }

    public static enum RenderMode {
        Fill,
        Outline,
        Both;

    }
}

