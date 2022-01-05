/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.command;

import me.gerald.hack.GeraldHack;

public class Command {
    private String name;
    private String description;
    private String[] usage;

    public Command(String name, String description, String[] usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    public void onCommand(String[] args) {
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getUsage() {
        return this.usage;
    }

    public String getPrefix() {
        return GeraldHack.PREFIX;
    }
}

