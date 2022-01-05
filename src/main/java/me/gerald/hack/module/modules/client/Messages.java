/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.gerald.hack.module.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.event.events.ModuleToggleEvent;
import me.gerald.hack.module.Module;
import me.gerald.hack.util.MessageUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Messages
extends Module {
    public Messages() {
        super("Messages", Module.Category.CLIENT, "Sends a message when you toggle a module.");
        this.setNeedsKeybind(false);
    }

    @SubscribeEvent
    public void onModuleEnable(ModuleToggleEvent.Pre event) {
        MessageUtil.sendClientMessage((Object)ChatFormatting.BOLD + event.getModule().getName() + (Object)ChatFormatting.GREEN + " ENABLED!");
    }

    @SubscribeEvent
    public void onModuleDisable(ModuleToggleEvent.Post event) {
        MessageUtil.sendClientMessage((Object)ChatFormatting.BOLD + event.getModule().getName() + (Object)ChatFormatting.RED + " DISABLED!");
    }
}

