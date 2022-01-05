/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.item.ItemBow
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.NumberUtil;
import me.gerald.hack.util.TimerUtil;
import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BowSpam
extends Module {
    public NumSetting delay = this.register(new NumSetting("Delay", 0.1f, 0.0f, 6.0f));
    public NumberUtil shotsFired = new NumberUtil(0);
    public TimerUtil timeFromLastShow = new TimerUtil();

    public BowSpam() {
        super("BowSpam", Module.Category.COMBAT, "Spams arrows.");
    }

    @Override
    public String getMetaData() {
        return this.shotsFired.getValue() != 0.0f ? "[" + (Object)ChatFormatting.GRAY + this.shotsFired.getValue() + (Object)ChatFormatting.RESET + "]" : "";
    }

    @SubscribeEvent
    public void onUseItem(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (BowSpam.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && BowSpam.mc.player.isHandActive() && BowSpam.mc.player.getItemInUseMaxCount() >= 3 + (int)this.delay.getValue()) {
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, BowSpam.mc.player.getHorizontalFacing()));
            BowSpam.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(BowSpam.mc.player.getActiveHand()));
            BowSpam.mc.player.stopActiveHand();
            this.shotsFired.increase(1.0f);
            this.timeFromLastShow.reset();
        }
        if (this.timeFromLastShow.passedMs(1000L)) {
            this.shotsFired.zero();
            this.timeFromLastShow.reset();
        }
    }
}

