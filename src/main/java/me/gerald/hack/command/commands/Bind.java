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
import me.gerald.hack.module.Module;
import me.gerald.hack.util.MessageUtil;
import org.lwjgl.input.Keyboard;

public class Bind
extends Command {
    public Bind() {
        super("Bind", "Binds a module to a key.", new String[]{"bind", "[module]", "[bind]"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length < 2) {
            MessageUtil.sendErrorMessage("Please specify a module.");
            return;
        }
        if (args.length < 3) {
            MessageUtil.sendErrorMessage("Please specify a bind.");
            return;
        }
        String moduleName = args[1];
        String key = args[2];
        for (Module module : GeraldHack.INSTANCE.moduleManager.getModules()) {
            if (!module.getName().equalsIgnoreCase(moduleName)) continue;
            try {
                module.setKeybind(Keyboard.getKeyIndex((String)key.toUpperCase()));
                MessageUtil.sendClientMessage("Bound " + (Object)ChatFormatting.GRAY + module.getName() + (Object)ChatFormatting.RESET + " to " + (Object)ChatFormatting.GRAY + "[" + (Object)ChatFormatting.GREEN + Keyboard.getKeyName((int)Keyboard.getKeyIndex((String)key.toUpperCase())) + (Object)ChatFormatting.GRAY + "]");
            }
            catch (Exception e) {
                e.printStackTrace();
                MessageUtil.sendErrorMessage("Failed to bind " + module.getName() + " to " + (Object)ChatFormatting.GRAY + "[" + (Object)ChatFormatting.GREEN + Keyboard.getKeyName((int)Keyboard.getKeyIndex((String)key.toUpperCase())) + (Object)ChatFormatting.GRAY + "]" + (Object)ChatFormatting.RESET + " please make sure everything is correct.");
            }
        }
    }
}

