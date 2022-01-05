/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.init.Items
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.event.events.TotemPopEvent;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.InventoryUtil;
import me.gerald.hack.util.MessageUtil;
import me.gerald.hack.util.TimerUtil;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoTotem
extends Module {
    public NumSetting delay = this.register(new NumSetting("Delay", 0.0f, 0.0f, 250.0f));
    public BoolSetting message = this.register(new BoolSetting("Message", true));
    public boolean needsTotem;
    public TimerUtil timer = new TimerUtil();

    public AutoTotem() {
        super("AutoTotem", Module.Category.COMBAT, "Puts a totem in your offhand.");
    }

    @Override
    public String getMetaData() {
        return "[" + (Object)ChatFormatting.GRAY + InventoryUtil.getTotalAmountOfItem(Items.TOTEM_OF_UNDYING) + (Object)ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onPop(TotemPopEvent event) {
        if (event.getEntity() == AutoTotem.mc.player) {
            this.needsTotem = true;
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (AutoTotem.mc.player.isCreative()) {
            return;
        }
        int slot = InventoryUtil.getItemInventory(Items.TOTEM_OF_UNDYING, false);
        if (AutoTotem.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING && !this.needsTotem) {
            this.needsTotem = true;
            this.timer.reset();
        }
        if (this.needsTotem && this.timer.passedMs((long)this.delay.getValue()) && slot != -1) {
            InventoryUtil.moveItemToSlot(slot, 45);
            if (this.message.getValue()) {
                MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Moved " + (Object)ChatFormatting.GREEN + "Totem " + (Object)ChatFormatting.GRAY + "to offhand.");
            }
            this.needsTotem = false;
            this.timer.reset();
        }
    }
}

