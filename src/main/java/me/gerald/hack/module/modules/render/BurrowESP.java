/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.entity.player.EntityPlayer
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
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class BurrowESP
extends Module {
    public ModeSetting renderMode = this.register(new ModeSetting("RenderMode", RenderMode.Both));
    public ColorSetting color = this.register(new ColorSetting("Color", 0.0f, 125.0f, 255.0f, 43.0f));
    public BoolSetting rainbow = this.register(new BoolSetting("Rainbow", false));
    public NumSetting rainbowSpeed = this.register(new NumSetting("RainbowSpeed", 6.0f, 1.0f, 10.0f));
    public NumSetting lineWidth = this.register(new NumSetting("LineWidth", 2.0f, 0.0f, 6.0f));
    public BoolSetting self = this.register(new BoolSetting("Self", true));

    public BurrowESP() {
        super("BurrowESP", Module.Category.RENDER, "Renders players in burrow.");
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (this.nullCheck()) {
            return;
        }
        for (EntityPlayer entity : BurrowESP.mc.world.playerEntities) {
            Block[] blocks = new Block[]{Blocks.OBSIDIAN, Blocks.ANVIL, Blocks.ENDER_CHEST};
            if (entity == BurrowESP.mc.player && !this.self.getValue()) continue;
            for (Block block : blocks) {
                if (BurrowESP.mc.world.getBlockState(entity.getPosition()).getBlock() != block) continue;
                AxisAlignedBB box = BurrowESP.mc.world.getBlockState(new BlockPos(Math.floor(entity.getPosition().getX()), Math.floor(entity.getPosition().getY()), Math.floor(entity.getPosition().getZ()))).getSelectedBoundingBox((World)BurrowESP.mc.world, entity.getPosition()).offset(-BurrowESP.mc.getRenderManager().viewerPosX, -BurrowESP.mc.getRenderManager().viewerPosY, -BurrowESP.mc.getRenderManager().viewerPosZ);
                float r = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getRed() : this.color.getR()) / 255.0f;
                float g = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getGreen() : this.color.getG()) / 255.0f;
                float b = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getBlue() : this.color.getB()) / 255.0f;
                RenderUtil.prepare();
                GL11.glLineWidth((float)this.lineWidth.getValue());
                if (this.renderMode.getValueEnum() == RenderMode.Fill || this.renderMode.getValueEnum() == RenderMode.Both) {
                    RenderGlobal.renderFilledBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)r, (float)g, (float)b, (float)(this.color.getA() / 255.0f));
                }
                if (this.renderMode.getValueEnum() == RenderMode.Outline || this.renderMode.getValueEnum() == RenderMode.Both) {
                    RenderGlobal.drawBoundingBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)r, (float)g, (float)b, (float)1.0f);
                }
                RenderUtil.release();
            }
        }
    }

    public static enum RenderMode {
        Fill,
        Outline,
        Both;

    }
}

