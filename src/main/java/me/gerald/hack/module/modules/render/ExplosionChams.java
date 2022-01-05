/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.network.play.server.SPacketDestroyEntities
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package me.gerald.hack.module.modules.render;

import java.util.HashMap;
import java.util.Map;
import me.gerald.hack.event.events.PacketEvent;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.ModeSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ExplosionChams
extends Module {
    public NumSetting timeToRemove = this.register(new NumSetting("TimeToRemove", 1500.0f, 0.0f, 3000.0f));
    public ModeSetting renderMode = this.register(new ModeSetting("RenderMode", RenderMode.Both));
    public ColorSetting color = this.register(new ColorSetting("Color", 0.0f, 125.0f, 255.0f, 43.0f));
    public BoolSetting rainbow = this.register(new BoolSetting("Rainbow", false));
    public NumSetting rainbowSpeed = this.register(new NumSetting("RainbowSpeed", 6.0f, 1.0f, 10.0f));
    public NumSetting lineWidth = this.register(new NumSetting("LineWidth", 2.0f, 1.0f, 4.0f));
    private static final HashMap<EntityEnderCrystal, Long> crystalExplosionMap = new HashMap();

    public ExplosionChams() {
        super("ExplosionChams", Module.Category.RENDER, "Renders explosion breaks.");
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (this.nullCheck()) {
            return;
        }
        for (Map.Entry<EntityEnderCrystal, Long> entry : new HashMap<EntityEnderCrystal, Long>(crystalExplosionMap).entrySet()) {
            if (System.currentTimeMillis() - entry.getValue() > (long)this.timeToRemove.getValue()) {
                crystalExplosionMap.remove((Object)entry.getKey());
                continue;
            }
            float r = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getRed() : this.color.getR()) / 255.0f;
            float g = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getGreen() : this.color.getG()) / 255.0f;
            float b = (this.rainbow.getValue() ? (float)RenderUtil.genRainbow((int)this.rainbowSpeed.getValue()).getBlue() : this.color.getB()) / 255.0f;
            GL11.glPushMatrix();
            GL11.glDepthRange((double)0.0, (double)0.01f);
            if (this.renderMode.getValueEnum() == RenderMode.Wireframe || this.renderMode.getValueEnum() == RenderMode.Both) {
                GL11.glDisable((int)2896);
                GL11.glDisable((int)3553);
                GL11.glPolygonMode((int)1032, (int)6913);
                GL11.glEnable((int)3008);
                GL11.glEnable((int)3042);
                GL11.glLineWidth((float)this.lineWidth.getValue());
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glColor4f((float)r, (float)g, (float)b, (float)1.0f);
                this.renderEntityStatic((Entity)entry.getKey(), event.getPartialTicks(), true);
                GL11.glHint((int)3154, (int)4352);
                GL11.glPolygonMode((int)1032, (int)6914);
                GL11.glEnable((int)2896);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glEnable((int)3553);
            }
            if (this.renderMode.getValueEnum() == RenderMode.Fill || this.renderMode.getValueEnum() == RenderMode.Both) {
                GL11.glPushAttrib((int)-1);
                GL11.glEnable((int)3008);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glEnable((int)3042);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
                GL11.glLineWidth((float)1.5f);
                GL11.glColor4f((float)r, (float)g, (float)b, (float)(this.color.getA() / 255.0f));
                this.renderEntityStatic((Entity)entry.getKey(), event.getPartialTicks(), true);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glDisable((int)3008);
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2896);
                GL11.glDisable((int)3042);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glPopAttrib();
            }
            GL11.glDepthRange((double)0.0, (double)1.0);
            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public void onBreak(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketDestroyEntities) {
            SPacketDestroyEntities packet = (SPacketDestroyEntities)event.getPacket();
            for (Entity entity : ExplosionChams.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityEnderCrystal)) continue;
                for (int array : packet.getEntityIDs()) {
                    if (array != entity.getEntityId()) continue;
                    EntityEnderCrystal fakeEntity = new EntityEnderCrystal((World)ExplosionChams.mc.world, entity.posX, entity.posY, entity.posZ);
                    fakeEntity.innerRotation = 0;
                    fakeEntity.setShowBottom(false);
                    crystalExplosionMap.put(fakeEntity, System.currentTimeMillis());
                }
            }
        }
    }

    public void renderEntityStatic(Entity entityIn, float partialTicks, boolean p_188388_3_) {
        if (entityIn.ticksExisted == 0) {
            entityIn.lastTickPosX = entityIn.posX;
            entityIn.lastTickPosY = entityIn.posY;
            entityIn.lastTickPosZ = entityIn.posZ;
        }
        double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)partialTicks;
        double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)partialTicks;
        double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)partialTicks;
        float f = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
        int i = entityIn.getBrightnessForRender();
        if (entityIn.isBurning()) {
            i = 0xF000F0;
        }
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords((int)OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        mc.getRenderManager().renderEntity(entityIn, d0 - ExplosionChams.mc.getRenderManager().viewerPosX, d1 - ExplosionChams.mc.getRenderManager().viewerPosY, d2 - ExplosionChams.mc.getRenderManager().viewerPosZ, f, partialTicks, p_188388_3_);
    }

    public static enum RenderMode {
        Wireframe,
        Fill,
        Both;

    }
}

