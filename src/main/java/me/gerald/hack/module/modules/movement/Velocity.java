/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketEntityVelocity
 *  net.minecraft.network.play.server.SPacketExplosion
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.gerald.hack.module.modules.movement;

import me.gerald.hack.event.events.PacketEvent;
import me.gerald.hack.module.Module;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity
extends Module {
    public Velocity() {
        super("Velocity", Module.Category.MOVEMENT, "Cancels velocity packets.");
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityVelocity || event.getPacket() instanceof SPacketExplosion) {
            event.setCanceled(true);
        }
    }
}

