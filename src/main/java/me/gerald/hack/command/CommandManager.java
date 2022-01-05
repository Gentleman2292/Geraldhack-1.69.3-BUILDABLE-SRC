/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.command;

import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.command.Command;
import me.gerald.hack.command.commands.Bind;
import me.gerald.hack.command.commands.Copy;
import me.gerald.hack.command.commands.FakePlayer;
import me.gerald.hack.command.commands.Friend;
import me.gerald.hack.command.commands.Help;
import me.gerald.hack.command.commands.Ping;
import me.gerald.hack.command.commands.Prefix;
import me.gerald.hack.command.commands.SetSetting;
import me.gerald.hack.command.commands.Toggle;

public class CommandManager {
    public List<Command> commands = new ArrayList<Command>();

    public CommandManager() {
        this.commands.add(new Bind());
        this.commands.add(new Copy());
        this.commands.add(new FakePlayer());
        this.commands.add(new Friend());
        this.commands.add(new Help());
        this.commands.add(new Ping());
        this.commands.add(new Prefix());
        this.commands.add(new SetSetting());
        this.commands.add(new Toggle());
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}

