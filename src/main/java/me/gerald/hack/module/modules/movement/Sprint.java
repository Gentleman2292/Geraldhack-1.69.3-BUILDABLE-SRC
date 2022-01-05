/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package me.gerald.hack.module.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.ModeSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint
extends Module {
    public ModeSetting mode = this.register(new ModeSetting("Mode", Mode.Rage));

    public Sprint() {
        super("Sprint", Module.Category.MOVEMENT, "Automatically sprints for you.");
    }

    @Override
    public String getMetaData() {
        return "[" + (Object)ChatFormatting.GRAY + this.mode.getValueEnum() + (Object)ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.mode.getValueEnum() == Mode.Rage) {
            if (!Sprint.mc.player.isSprinting()) {
                Sprint.mc.player.setSprinting(true);
            }
        } else if (!(this.mode.getValueEnum() != Mode.Legit || (Sprint.mc.player.collidedHorizontally || Sprint.mc.player.isSneaking() || Sprint.mc.player.moveForward == 0.0f) && Sprint.mc.player.moveStrafing == 0.0f || Sprint.mc.player.isSprinting())) {
            Sprint.mc.player.setSprinting(true);
        }
    }

    public static enum Mode {
        Rage,
        Legit;

    }
}

