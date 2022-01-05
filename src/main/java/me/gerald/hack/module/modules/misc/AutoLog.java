/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.init.MobEffects
 *  net.minecraft.network.play.server.SPacketDisconnect
 *  net.minecraft.util.text.ITextComponent
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package me.gerald.hack.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Objects;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoLog
extends Module {
    public NumSetting healthToLog = this.register(new NumSetting("HealthToLog", 10.0f, 0.0f, 36.0f));
    public BoolSetting weakness = this.register(new BoolSetting("Weakness", true));

    public AutoLog() {
        super("AutoLog", Module.Category.MISC, "Automatically logs for you.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (AutoLog.mc.player.isCreative()) {
            return;
        }
        if (AutoLog.mc.player.getHealth() + AutoLog.mc.player.getAbsorptionAmount() <= this.healthToLog.getValue()) {
            Objects.requireNonNull(mc.getConnection()).handleDisconnect(new SPacketDisconnect((ITextComponent)new TextComponentString("Logged at " + (Object)ChatFormatting.GRAY + "[" + (AutoLog.mc.player.getHealth() + AutoLog.mc.player.getAbsorptionAmount()) + "]")));
            this.toggle();
        }
        if (AutoLog.mc.player.isPotionActive(MobEffects.WEAKNESS) && this.weakness.getValue()) {
            Objects.requireNonNull(mc.getConnection()).handleDisconnect(new SPacketDisconnect((ITextComponent)new TextComponentString("Logged because you got " + (Object)ChatFormatting.GRAY + "WEAKNESSED" + (Object)ChatFormatting.RESET + " ew!")));
            this.toggle();
        }
    }
}

