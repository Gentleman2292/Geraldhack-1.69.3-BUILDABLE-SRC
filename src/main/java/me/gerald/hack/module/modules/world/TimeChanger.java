/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.network.play.server.SPacketTimeUpdate
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.event.events.PacketEvent;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TimeChanger
extends Module {
    public NumSetting time = this.register(new NumSetting("Time", 12500.0f, 0.0f, 24000.0f));

    public TimeChanger() {
        super("TimeChanger", Module.Category.WORLD, "Changes the world time.");
    }

    @Override
    public String getMetaData() {
        return "[" + (Object)ChatFormatting.GRAY + (int)this.time.getValue() + (Object)ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        TimeChanger.mc.world.setWorldTime((long)this.time.getValue());
    }

    @SubscribeEvent
    public void onTimeUpdate(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            event.setCanceled(true);
        }
    }
}

