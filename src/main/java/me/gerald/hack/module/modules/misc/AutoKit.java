/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.event.events.PlayerDeathEvent;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.setting.settings.StringSetting;
import me.gerald.hack.util.TimerUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoKit
extends Module {
    public StringSetting kitName = this.register(new StringSetting("KitName", "autoKit"));
    public NumSetting delay = this.register(new NumSetting("Delay", 50.0f, 0.0f, 250.0f));
    public boolean hasDied = false;
    public TimerUtil timer = new TimerUtil();

    public AutoKit() {
        super("AutoKit", Module.Category.MISC, "Automatically does /kit [kitName] for you");
    }

    @Override
    public String getMetaData() {
        return "[" + (Object)ChatFormatting.GRAY + this.kitName.getString() + (Object)ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity() == AutoKit.mc.player && !this.hasDied) {
            this.hasDied = true;
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.hasDied && AutoKit.mc.player.isEntityAlive() && !AutoKit.mc.player.isDead && AutoKit.mc.player.getHealth() > 1.0f && this.timer.passedMs((long)this.delay.getValue())) {
            AutoKit.mc.player.sendChatMessage("/kit " + this.kitName.getString());
            this.timer.reset();
            this.hasDied = false;
        }
    }

    @Override
    public void onDisable() {
        if (this.hasDied) {
            this.hasDied = false;
        }
    }
}

