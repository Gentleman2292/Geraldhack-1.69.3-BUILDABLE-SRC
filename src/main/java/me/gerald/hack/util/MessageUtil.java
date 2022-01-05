/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 */
package me.gerald.hack.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class MessageUtil {
    public static String clientPrefix = (Object)ChatFormatting.GRAY + "-" + (Object)ChatFormatting.AQUA + "Gerald" + (Object)ChatFormatting.GRAY + "(Hack)- " + (Object)ChatFormatting.RESET;
    public static String errorPrefix = (Object)ChatFormatting.GRAY + "-" + (Object)ChatFormatting.RED + "Gerald" + (Object)ChatFormatting.GRAY + "(Hack)- " + (Object)ChatFormatting.RED;

    public static void sendRawMessage(String message) {
        Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentString(message));
    }

    public static void sendClientMessage(String message) {
        MessageUtil.sendRawMessage(clientPrefix + message);
    }

    public static void sendErrorMessage(String message) {
        MessageUtil.sendRawMessage(errorPrefix + message);
    }

    public static void sendRemovableMessage(String message, int id) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(clientPrefix + message), id);
    }
}

