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

public class VoidESP
extends Module {
    public NumSetting range = this.register(new NumSetting("Range", 12.0f, 0.0f, 30.0f));
    public NumSetting yToAdd = this.register(new NumSetting("YToAdd", 0.0f, -1.0f, 4.0f));
    public ModeSetting renderMode = this.register(new ModeSetting("RenderMode", RenderMode.Both));
    public ColorSetting color = this.register(new ColorSetting("Color", 0.0f, 125.0f, 255.0f, 43.0f));
    public BoolSetting rainbow = this.register(new BoolSetting("Rainbow", false));
    public NumSetting rainbowSpeed = this.register(new NumSetting("RainbowSpeed", 6.0f, 1.0f, 10.0f));
    public NumSetting lineWidth = this.register(new NumSetting("LineWidth", 2.0f, 0.0f, 4.0f));

    public VoidESP() {
        super("VoidESP", Module.Category.RENDER, "Renders void holes.");
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (this.nullCheck()) {
            return;
        }
        for (BlockPos pos : WorldUtil.getSphere(VoidESP.mc.player.getPosition(), this.range.getValue(), false)) {
            if (pos.getY() != 0 || VoidESP.mc.world.getBlockState(pos).getBlock() != Blocks.AIR) continue;
            AxisAlignedBB box = VoidESP.mc.world.getBlockState(pos).getSelectedBoundingBox((World)VoidESP.mc.world, pos).offset(-VoidESP.mc.getRenderManager().viewerPosX, -VoidESP.mc.getRenderManager().viewerPosY, -VoidESP.mc.getRenderManager().viewerPosZ);
            float r = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getRed() : this.color.getR()) / 255.0f;
            float g = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getGreen() : this.color.getG()) / 255.0f;
            float b = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getBlue() : this.color.getB()) / 255.0f;
            RenderUtil.prepare();
            GL11.glLineWidth((float)this.lineWidth.getValue());
            if (this.renderMode.getValueEnum() == RenderMode.Fill || this.renderMode.getValueEnum() == RenderMode.Both) {
                RenderGlobal.renderFilledBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)(box.maxY + (double)this.yToAdd.getValue()), (double)box.maxZ, (float)r, (float)g, (float)b, (float)(this.color.getA() / 255.0f));
            }
            if (this.renderMode.getValueEnum() == RenderMode.Outline || this.renderMode.getValueEnum() == RenderMode.Both) {
                RenderGlobal.drawBoundingBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)(box.maxY + (double)this.yToAdd.getValue()), (double)box.maxZ, (float)r, (float)g, (float)b, (float)1.0f);
            }
            RenderUtil.release();
        }
    }

    public static enum RenderMode {
        Fill,
        Outline,
        Both;

    }
}

