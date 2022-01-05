/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.gerald.hack.module.modules.render;

import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.ModeSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ESP
extends Module {
    public ModeSetting renderMode = this.register(new ModeSetting("RenderMode", RenderMode.Both));
    public ColorSetting color = this.register(new ColorSetting("Color", 0.0f, 125.0f, 255.0f, 43.0f));
    public BoolSetting rainbow = this.register(new BoolSetting("Rainbow", false));
    public NumSetting rainbowSpeed = this.register(new NumSetting("RainbowSpeed", 6.0f, 1.0f, 10.0f));
    public NumSetting lineWidth = this.register(new NumSetting("LineWidth", 2.0f, 0.0f, 4.0f));
    public BoolSetting players = this.register(new BoolSetting("Players", true));
    public BoolSetting items = this.register(new BoolSetting("Items", true));
    public BoolSetting mobs = this.register(new BoolSetting("Mobs", true));
    public BoolSetting animals = this.register(new BoolSetting("Animals", true));
    public EntityPlayer player;

    public ESP() {
        super("ESP", Module.Category.RENDER, "Renders boxes around entities.");
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        for (Entity e : ESP.mc.world.loadedEntityList) {
            if (e == ESP.mc.player) continue;
            RenderUtil.prepare();
            GlStateManager.glLineWidth((float)this.lineWidth.getValue());
            if (e instanceof EntityPlayer && this.players.getValue()) {
                this.render(e);
            }
            if (e instanceof EntityItem && this.items.getValue()) {
                this.render(e);
            }
            if (e instanceof EntityMob || e instanceof EntitySlime && this.mobs.getValue()) {
                this.render(e);
            }
            if (e instanceof EntityAnimal && this.animals.getValue()) {
                this.render(e);
            }
            RenderUtil.release();
        }
    }

    public void render(Entity entity) {
        AxisAlignedBB box = entity.getRenderBoundingBox();
        box = box.offset(-ESP.mc.getRenderManager().viewerPosX, -ESP.mc.getRenderManager().viewerPosY, -ESP.mc.getRenderManager().viewerPosZ);
        float r = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getRed() : this.color.getR()) / 255.0f;
        float g = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getGreen() : this.color.getG()) / 255.0f;
        float b = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getBlue() : this.color.getB()) / 255.0f;
        if (this.renderMode.getValueEnum() == RenderMode.Fill || this.renderMode.getValueEnum() == RenderMode.Both) {
            RenderGlobal.renderFilledBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)r, (float)g, (float)b, (float)(this.color.getA() / 255.0f));
        }
        if (this.renderMode.getValueEnum() == RenderMode.Outline || this.renderMode.getValueEnum() == RenderMode.Both) {
            RenderGlobal.drawBoundingBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)r, (float)g, (float)b, (float)1.0f);
        }
    }

    public static enum RenderMode {
        Fill,
        Outline,
        Both;

    }
}

