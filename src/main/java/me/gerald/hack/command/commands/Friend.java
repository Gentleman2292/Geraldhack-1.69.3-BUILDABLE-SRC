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

public class Friend
extends Command {
    public Friend() {
        super("Friend", "Adds a friend.", new String[]{"friend", "[add/del/get]", "[name]"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length < 2) {
            MessageUtil.sendErrorMessage("Please specify add/del.");
            return;
        }
        String secondString = args[1];
        if (secondString.equalsIgnoreCase("get")) {
            if (GeraldHack.INSTANCE.friendManager.getFriends().isEmpty()) {
                MessageUtil.sendErrorMessage("You have no friends lol.");
                return;
            }
            MessageUtil.sendClientMessage("Friends:");
            for (me.gerald.hack.util.friend.Friend friend : GeraldHack.INSTANCE.friendManager.getFriends()) {
                MessageUtil.sendClientMessage((Object)ChatFormatting.AQUA + friend.getName());
            }
            return;
        }
        if (args.length < 3) {
            MessageUtil.sendErrorMessage("Please specify player you wish to friend/unfriend.");
            return;
        }
        String playerName = args[2];
        if (secondString.equalsIgnoreCase("add")) {
            if (GeraldHack.INSTANCE.friendManager.isFriend(playerName)) {
                MessageUtil.sendErrorMessage((Object)ChatFormatting.GREEN + playerName + (Object)ChatFormatting.RESET + " is already a friend.");
                return;
            }
            GeraldHack.INSTANCE.friendManager.addFriend(playerName);
            MessageUtil.sendClientMessage((Object)ChatFormatting.GREEN + playerName + (Object)ChatFormatting.RESET + " has been added to friend list.");
        } else if (secondString.equalsIgnoreCase("del") || secondString.equalsIgnoreCase("delete") || secondString.equalsIgnoreCase("remove")) {
            if (!GeraldHack.INSTANCE.friendManager.isFriend(playerName)) {
                MessageUtil.sendErrorMessage((Object)ChatFormatting.RED + playerName + (Object)ChatFormatting.RESET + " is already not a friend.");
                return;
            }
            GeraldHack.INSTANCE.friendManager.delFriend(playerName);
            MessageUtil.sendClientMessage((Object)ChatFormatting.RED + playerName + (Object)ChatFormatting.RESET + " has been removed from friend list.");
        }
    }
}

