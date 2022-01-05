/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.apache.commons.lang3.RandomStringUtils
 */
package me.gerald.hack.module.modules.misc;

import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.setting.settings.StringSetting;
import me.gerald.hack.util.TimerUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.RandomStringUtils;

public class Spammer
extends Module {
    public NumSetting delay = this.register(new NumSetting("Delay", 3.0f, 0.0f, 5.0f));
    public BoolSetting randomNumbers = this.register(new BoolSetting("RandomNumbers", true));
    public BoolSetting customString = this.register(new BoolSetting("CustomString", true));
    public StringSetting string = this.register(new StringSetting("String", "Gerald(Hack) on top!"));
    public TimerUtil timer = new TimerUtil();

    public Spammer() {
        super("Spammer", Module.Category.MISC, "Spams chat.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.timer.passedMs((long)this.delay.getValue() * 1000L)) {
            Spammer.mc.player.sendChatMessage((this.customString.getValue() ? this.string.getString() : "Gerald(Hack) on top!") + (this.randomNumbers.getValue() ? " | " + RandomStringUtils.random((int)6, (boolean)true, (boolean)true) : ""));
            this.timer.reset();
        }
    }
}

