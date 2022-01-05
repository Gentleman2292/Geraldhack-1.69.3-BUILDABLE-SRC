/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module.modules.player;

import me.gerald.hack.module.Module;

public class Suicide
extends Module {
    public Suicide() {
        super("Suicide", Module.Category.PLAYER, "Automatically kills the player.");
    }

    @Override
    public void onEnable() {
        Suicide.mc.player.sendChatMessage("/kill");
        this.toggle();
    }
}

