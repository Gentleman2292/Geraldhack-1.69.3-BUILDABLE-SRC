/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.gerald.hack.module.modules.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.event.events.PlayerDeathEvent;
import me.gerald.hack.event.events.TotemPopEvent;
import me.gerald.hack.module.Module;
import me.gerald.hack.util.MessageUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TotemPopCounter
extends Module {
    public TotemPopCounter() {
        super("TotemPopCounter", Module.Category.WORLD, "Says in chat when someone pops.");
    }

    @SubscribeEvent
    public void onPop(TotemPopEvent event) {
        if (event.getPopCount() == 1) {
            MessageUtil.sendRemovableMessage((Object)ChatFormatting.AQUA + event.getEntity().getDisplayName().getFormattedText() + (Object)ChatFormatting.GRAY + " has just popped a totem.", event.getEntity().getEntityId());
        } else if (event.getPopCount() > 1) {
            MessageUtil.sendRemovableMessage((Object)ChatFormatting.AQUA + event.getEntity().getDisplayName().getFormattedText() + (Object)ChatFormatting.GRAY + " has just popped " + (Object)ChatFormatting.RED + event.getPopCount() + (Object)ChatFormatting.GRAY + " totems.", event.getEntity().getEntityId());
        }
    }

    @SubscribeEvent
    public void onDeath(PlayerDeathEvent event) {
        MessageUtil.sendRemovableMessage((Object)ChatFormatting.AQUA + event.getEntity().getDisplayName().getFormattedText() + (Object)ChatFormatting.GRAY + " has just died what a retard.", event.getEntity().getEntityId());
    }
}

