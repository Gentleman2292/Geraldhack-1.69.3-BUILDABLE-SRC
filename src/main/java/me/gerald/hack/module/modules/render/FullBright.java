/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.render;

import me.gerald.hack.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FullBright
extends Module {
    float originalValue;

    public FullBright() {
        super("FullBright", Module.Category.RENDER, "LET THERE BE LIGHT!!!");
        this.setNeedsKeybind(false);
    }

    @Override
    public void onEnable() {
        this.originalValue = FullBright.mc.gameSettings.gammaSetting;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        FullBright.mc.gameSettings.gammaSetting = 100.0f;
    }

    @Override
    public void onDisable() {
        FullBright.mc.gameSettings.gammaSetting = this.originalValue;
    }
}

