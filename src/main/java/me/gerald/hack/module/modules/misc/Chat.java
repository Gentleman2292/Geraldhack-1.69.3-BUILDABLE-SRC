/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.client.event.ClientChatEvent
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.gerald.hack.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.SimpleDateFormat;
import java.util.Date;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Chat
extends Module {
    public BoolSetting suffix = this.register(new BoolSetting("Suffix", true));
    public BoolSetting timeStamp = this.register(new BoolSetting("Timestamp", true));
    public BoolSetting greenText = this.register(new BoolSetting("GreenText", false));

    public Chat() {
        super("Chat", Module.Category.MISC, "Change things with chat.");
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (event.getOriginalMessage().startsWith("/") || event.getOriginalMessage().startsWith("!")) {
            return;
        }
        if (this.suffix.getValue()) {
            event.setMessage(event.getOriginalMessage() + " \u0262\u1d07\u0280\u1d00\u029f\u1d05(\u029c\u1d00\u1d04\u1d0b)");
        }
        if (this.greenText.getValue()) {
            event.setMessage("> " + event.getMessage());
        }
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        String timeString = time.format(new Date());
        if (this.timeStamp.getValue()) {
            event.setMessage(new TextComponentString((Object)ChatFormatting.GRAY + "<" + (Object)ChatFormatting.GREEN + timeString + (Object)ChatFormatting.GRAY + "> " + (Object)ChatFormatting.RESET).appendSibling(event.getMessage()));
        }
    }
}

