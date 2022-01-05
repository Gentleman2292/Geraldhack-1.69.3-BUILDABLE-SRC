/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.event.listeners;

import me.gerald.hack.event.events.MCPlayerKillEvent;
import me.gerald.hack.event.events.PacketEvent;
import me.gerald.hack.event.events.PlayerDeathEvent;
import me.gerald.hack.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KillListener {
    public EntityPlayer playerAttacking = null;
    public TimerUtil timer = new TimerUtil();

    public KillListener() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)Minecraft.getMinecraft().world) instanceof EntityPlayer) {
            this.playerAttacking = (EntityPlayer)packet.getEntityFromWorld((World)Minecraft.getMinecraft().world);
            this.timer.reset();
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (event.getTarget() instanceof EntityPlayer) {
            this.playerAttacking = (EntityPlayer)event.getTarget();
            this.timer.reset();
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity() == this.playerAttacking) {
            MinecraftForge.EVENT_BUS.post((Event)new MCPlayerKillEvent(this.playerAttacking));
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.playerAttacking != null && this.timer.passedMs(5000L)) {
            this.playerAttacking = null;
        }
    }
}

