/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.gerald.hack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Objects;
import me.gerald.hack.command.Command;
import me.gerald.hack.util.MessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class Ping
extends Command {
    public Ping() {
        super("Ping", "Says a players ping in chat.", new String[]{"ping", "[name]"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length < 2) {
            MessageUtil.sendErrorMessage("Please specify player you wish to see.");
            return;
        }
        String playerName = args[1];
        for (EntityPlayer entity : Minecraft.getMinecraft().world.playerEntities) {
            if (!entity.getDisplayNameString().equalsIgnoreCase(playerName)) continue;
            MessageUtil.sendClientMessage("Ping for " + (Object)ChatFormatting.AQUA + playerName + (Object)ChatFormatting.RESET + ": " + Objects.requireNonNull(Minecraft.getMinecraft().getConnection()).getPlayerInfo(entity.getUniqueID()).getResponseTime());
        }
    }
}

