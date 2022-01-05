/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.monster.EntitySlime
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.opengl.GL11
 */
package me.gerald.hack.module.modules.combat;

import me.gerald.hack.GeraldHack;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.RenderUtil;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class KillAura
extends Module {
    public NumSetting range = this.register(new NumSetting("Range", 4.0f, 1.0f, 6.0f));
    public ColorSetting color = this.register(new ColorSetting("Color", 255.0f, 0.0f, 0.0f, 43.0f));
    public NumSetting lineWidth = this.register(new NumSetting("LineWidth", 1.0f, 0.0f, 3.0f));
    public BoolSetting players = this.register(new BoolSetting("Players", true));
    public BoolSetting animals = this.register(new BoolSetting("Animals", true));
    public BoolSetting mobs = this.register(new BoolSetting("Mobs", true));
    public Entity target = null;

    public KillAura() {
        super("KillAura", Module.Category.COMBAT, "Automatically attacks entities for you.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.target != null && (this.target.getDistance((Entity)KillAura.mc.player) >= this.range.getValue() || this.target.isDead || !this.target.isEntityAlive())) {
            this.target = null;
        }
        for (Entity entity : KillAura.mc.world.loadedEntityList) {
            if (entity == KillAura.mc.player || entity.isDead || !entity.isEntityAlive() || !(entity.getDistance((Entity)KillAura.mc.player) <= this.range.getValue()) || !(KillAura.mc.player.getCooledAttackStrength(0.0f) >= 1.0f)) continue;
            if (entity instanceof EntityPlayer && this.players.getValue()) {
                if (GeraldHack.INSTANCE.friendManager.isFriend(entity.getName())) continue;
                this.target = entity;
                this.attack(entity);
            }
            if (entity instanceof EntityAnimal && this.animals.getValue()) {
                this.target = entity;
                this.attack(entity);
            }
            if (!(entity instanceof EntityMob) && !(entity instanceof EntitySlime) || !this.mobs.getValue()) continue;
            this.target = entity;
            this.attack(entity);
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (this.target != null) {
            AxisAlignedBB box = this.target.getRenderBoundingBox().offset(-KillAura.mc.getRenderManager().viewerPosX, -KillAura.mc.getRenderManager().viewerPosY, -KillAura.mc.getRenderManager().viewerPosZ);
            RenderUtil.prepare();
            GL11.glLineWidth((float)this.lineWidth.getValue());
            RenderGlobal.drawBoundingBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)(this.color.getR() / 255.0f), (float)(this.color.getG() / 255.0f), (float)(this.color.getB() / 255.0f), (float)1.0f);
            RenderGlobal.renderFilledBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)(this.color.getR() / 255.0f), (float)(this.color.getG() / 255.0f), (float)(this.color.getB() / 255.0f), (float)(this.color.getA() / 255.0f));
            RenderUtil.release();
        }
    }

    public void attack(Entity entity) {
        KillAura.mc.playerController.attackEntity((EntityPlayer)KillAura.mc.player, entity);
        KillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
    }

    @Override
    public void onDisable() {
        if (this.target != null) {
            this.target = null;
        }
    }
}

