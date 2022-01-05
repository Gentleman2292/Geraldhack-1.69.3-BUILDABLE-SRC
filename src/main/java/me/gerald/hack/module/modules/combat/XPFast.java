/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package me.gerald.hack.module.modules.combat;

import me.gerald.hack.mixin.mixins.IMinecraft;
import me.gerald.hack.module.Module;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class XPFast
extends Module {
    public XPFast() {
        super("XPFast", Module.Category.PLAYER, "Uses stuff fast.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (XPFast.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            ((IMinecraft)mc).setRightClickDelayTimerAccessor(0);
        }
    }
}

