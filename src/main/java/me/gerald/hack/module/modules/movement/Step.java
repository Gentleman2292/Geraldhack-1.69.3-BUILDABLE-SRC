/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package me.gerald.hack.module.modules.movement;

import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Step
extends Module {
    public NumSetting stepHeight = this.register(new NumSetting("StepHeight", 1.5f, 0.5f, 2.0f));

    public Step() {
        super("Step", Module.Category.MOVEMENT, "Increases the player step height.");
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        Step.mc.player.stepHeight = this.stepHeight.getValue();
    }

    @Override
    public void onDisable() {
        Step.mc.player.stepHeight = 0.5f;
    }
}

