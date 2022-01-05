/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package me.gerald.hack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.command.Command;
import me.gerald.hack.util.MessageUtil;

public class Prefix
extends Command {
    public Prefix() {
        super("Prefix", "Set the prefix.", new String[]{"prefix", "[key]"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length < 2) {
            MessageUtil.sendErrorMessage("Please input a bind.");
            return;
        }
        String key = args[1];
        GeraldHack.INSTANCE.setPrefix(key.toUpperCase());
        MessageUtil.sendClientMessage("Set prefix to " + (Object)ChatFormatting.GRAY + "[" + (Object)ChatFormatting.GREEN + key.toUpperCase() + (Object)ChatFormatting.GRAY + "]");
    }
}

