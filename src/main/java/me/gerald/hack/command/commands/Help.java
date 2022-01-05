/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  org.lwjgl.input.Keyboard
 */
package me.gerald.hack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.command.Command;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.util.MessageUtil;
import org.lwjgl.input.Keyboard;

public class Help
extends Command {
    public Help() {
        super("Help", "Help command for the client.", new String[]{"help"});
    }

    @Override
    public void onCommand(String[] args) {
        MessageUtil.sendRawMessage((Object)ChatFormatting.GREEN + "Gerald(Hack)" + (Object)ChatFormatting.GRAY + "()" + (Object)ChatFormatting.RESET + " {");
        MessageUtil.sendRawMessage("    Creator: " + (Object)ChatFormatting.GREEN + "Guess?");
        MessageUtil.sendRawMessage("    Version: " + (Object)ChatFormatting.GREEN + "1.69.3");
        MessageUtil.sendRawMessage("    Command Prefix: " + (Object)ChatFormatting.GREEN + GeraldHack.PREFIX);
        MessageUtil.sendRawMessage("    ClickGUI Bind: " + (Object)ChatFormatting.GREEN + Keyboard.getKeyName((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).getKeybind()));
        MessageUtil.sendRawMessage("    " + (Object)ChatFormatting.GREEN + "Commands" + (Object)ChatFormatting.GRAY + "()" + (Object)ChatFormatting.RESET + " {");
        for (Command command : GeraldHack.INSTANCE.commandManager.getCommands()) {
            String delimiter = " ";
            MessageUtil.sendRawMessage("        " + (Object)ChatFormatting.GREEN + command.getName() + (Object)ChatFormatting.RESET + ": " + command.getDescription() + " Usage: " + String.join((CharSequence)delimiter, command.getUsage()));
        }
        MessageUtil.sendRawMessage("    }");
        MessageUtil.sendRawMessage("}");
    }
}

