/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.movement;

import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ReverseStep
extends Module {
    public NumSetting stepPower = this.register(new NumSetting("StepPower", 9.0f, 0.0f, 10.0f));

    public ReverseStep() {
        super("ReverseStep", Module.Category.MOVEMENT, "Pulls you down.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (!ReverseStep.mc.player.onGround) {
            ReverseStep.mc.player.motionY -= (double)this.stepPower.getValue();
        }
    }
}

