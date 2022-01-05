/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package me.gerald.hack.module.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.module.Module;
import me.gerald.hack.util.DiscordUtil;
import me.gerald.hack.util.MessageUtil;

public class DiscordRPC
extends Module {
    public DiscordRPC() {
        super("DiscordRPC", Module.Category.CLIENT, "Client RPC.");
        this.setNeedsKeybind(false);
    }

    @Override
    public void onEnable() {
        MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Starting DiscordRPC...");
        DiscordUtil.start();
    }

    @Override
    public void onDisable() {
        MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Stopping DiscordRPC...");
        DiscordUtil.stop();
    }
}

