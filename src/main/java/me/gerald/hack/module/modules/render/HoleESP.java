/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package me.gerald.hack.module.modules.render;

import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.ModeSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.RenderUtil;
import me.gerald.hack.util.WorldUtil;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class HoleESP
extends Module {
    public NumSetting range = this.register(new NumSetting("Range", 12.0f, 0.0f, 30.0f));
    public NumSetting yToAdd = this.register(new NumSetting("YToAdd", 0.0f, -1.0f, 4.0f));
    public ModeSetting renderMode = this.register(new ModeSetting("RenderMode", RenderMode.Both));
    public ColorSetting safeColor = this.register(new ColorSetting("SafeColor", 0.0f, 125.0f, 255.0f, 43.0f));
    public ColorSetting unSafeColor = this.register(new ColorSetting("UnSafeColor", 255.0f, 0.0f, 0.0f, 43.0f));
    public BoolSetting rainbow = this.register(new BoolSetting("Rainbow", false));
    public NumSetting rainbowSpeed = this.register(new NumSetting("RainbowSpeed", 6.0f, 1.0f, 10.0f));
    public NumSetting lineWidth = this.register(new NumSetting("LineWidth", 2.0f, 0.0f, 4.0f));

    public HoleESP() {
        super("HoleESP", Module.Category.RENDER, "Renders safe and unsafe holes.");
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        for (BlockPos pos : WorldUtil.getSphere(HoleESP.mc.player.getPosition(), this.range.getValue(), false)) {
            RenderUtil.prepare();
            AxisAlignedBB box = HoleESP.mc.world.getBlockState(pos).getSelectedBoundingBox((World)HoleESP.mc.world, pos).offset(-HoleESP.mc.getRenderManager().viewerPosX, -HoleESP.mc.getRenderManager().viewerPosY, -HoleESP.mc.getRenderManager().viewerPosZ);
            float r = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getRed() : (this.isSafeHole(pos) ? this.safeColor.getR() : this.unSafeColor.getR())) / 255.0f;
            float g = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getGreen() : (this.isSafeHole(pos) ? this.safeColor.getG() : this.unSafeColor.getG())) / 255.0f;
            float b = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getBlue() : (this.isSafeHole(pos) ? this.safeColor.getB() : this.unSafeColor.getB())) / 255.0f;
            if (this.isSafeHole(pos)) {
                GL11.glLineWidth((float)this.lineWidth.getValue());
                if (this.renderMode.getValueEnum() == RenderMode.Fill || this.renderMode.getValueEnum() == RenderMode.Both) {
                    RenderGlobal.renderFilledBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)(box.maxY + (double)this.yToAdd.getValue()), (double)box.maxZ, (float)r, (float)g, (float)b, (float)(this.safeColor.getA() / 255.0f));
                }
                if (this.renderMode.getValueEnum() == RenderMode.Outline || this.renderMode.getValueEnum() == RenderMode.Both) {
                    RenderGlobal.drawBoundingBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)(box.maxY + (double)this.yToAdd.getValue()), (double)box.maxZ, (float)r, (float)g, (float)b, (float)1.0f);
                }
            } else if (this.isUnSafeHole(pos)) {
                GL11.glLineWidth((float)this.lineWidth.getValue());
                if (this.renderMode.getValueEnum() == RenderMode.Fill || this.renderMode.getValueEnum() == RenderMode.Both) {
                    RenderGlobal.renderFilledBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)(box.maxY + (double)this.yToAdd.getValue()), (double)box.maxZ, (float)r, (float)g, (float)b, (float)(this.unSafeColor.getA() / 255.0f));
                }
                if (this.renderMode.getValueEnum() == RenderMode.Outline || this.renderMode.getValueEnum() == RenderMode.Both) {
                    RenderGlobal.drawBoundingBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)(box.maxY + (double)this.yToAdd.getValue()), (double)box.maxZ, (float)r, (float)g, (float)b, (float)1.0f);
                }
            }
            RenderUtil.release();
        }
    }

    public boolean isSafeHole(BlockPos pos) {
        return HoleESP.mc.world.getBlockState(pos).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(pos.up().up()).getBlock() == Blocks.AIR && HoleESP.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK;
    }

    public boolean isUnSafeHole(BlockPos pos) {
        return !(HoleESP.mc.world.getBlockState(pos).getBlock() != Blocks.AIR || HoleESP.mc.world.getBlockState(pos.down()).getBlock() != Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.down()).getBlock() != Blocks.OBSIDIAN || HoleESP.mc.world.getBlockState(pos.up()).getBlock() != Blocks.AIR || HoleESP.mc.world.getBlockState(pos.up().up()).getBlock() != Blocks.AIR || HoleESP.mc.world.getBlockState(pos.north()).getBlock() != Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.north()).getBlock() != Blocks.OBSIDIAN || HoleESP.mc.world.getBlockState(pos.south()).getBlock() != Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.south()).getBlock() != Blocks.OBSIDIAN || HoleESP.mc.world.getBlockState(pos.west()).getBlock() != Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.west()).getBlock() != Blocks.OBSIDIAN || HoleESP.mc.world.getBlockState(pos.east()).getBlock() != Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.east()).getBlock() != Blocks.OBSIDIAN);
    }

    public static enum RenderMode {
        Fill,
        Outline,
        Both;

    }
}

