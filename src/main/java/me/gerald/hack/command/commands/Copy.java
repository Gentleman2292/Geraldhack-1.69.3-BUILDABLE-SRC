/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ServerData
 */
package me.gerald.hack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import me.gerald.hack.command.Command;
import me.gerald.hack.util.MessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class Copy
extends Command {
    public Copy() {
        super("Copy", "Copies stuff to clipboard.", new String[]{"copy", "[ip/coords]"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length < 2) {
            MessageUtil.sendErrorMessage("Please specify what you want to copy.");
            return;
        }
        String copySubject = args[1];
        if (copySubject.equalsIgnoreCase("ip")) {
            ServerData data = Minecraft.getMinecraft().getCurrentServerData();
            try {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data.serverIP), null);
                MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Set clipboard to [" + (Object)ChatFormatting.GREEN + data.serverIP + (Object)ChatFormatting.GRAY + "]");
            }
            catch (Exception exception) {}
        } else if (copySubject.equalsIgnoreCase("coords")) {
            if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection((int)Minecraft.getMinecraft().player.posX + ", " + (int)Minecraft.getMinecraft().player.posY + ", " + (int)Minecraft.getMinecraft().player.posZ), null);
                MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Set clipboard to [" + (Object)ChatFormatting.GREEN + (int)Minecraft.getMinecraft().player.posX + ", " + (int)Minecraft.getMinecraft().player.posY + ", " + (int)Minecraft.getMinecraft().player.posZ + (Object)ChatFormatting.GRAY + "]");
            }
        } else {
            MessageUtil.sendErrorMessage("Please put the right things you can copy.");
        }
    }
}

