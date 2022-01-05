/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Items
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$MouseInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  org.lwjgl.input.Mouse
 */
package me.gerald.hack.module.modules.player;

import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.util.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class Pearl
extends Module {
    public BoolSetting silent = this.register(new BoolSetting("Silent", true));
    public boolean hasPressed = false;
    public int originalSlot = -1;

    public Pearl() {
        super("Pearl", Module.Category.PLAYER, "Throws pearls.");
    }

    @SubscribeEvent
    public void onInput(InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButtonState() && Mouse.getEventButton() == 2) {
            this.originalSlot = Pearl.mc.player.inventory.currentItem;
            this.hasPressed = true;
        } else if (!Mouse.getEventButtonState()) {
            this.hasPressed = false;
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        int slot;
        if (this.nullCheck()) {
            return;
        }
        if (this.hasPressed && (slot = InventoryUtil.getItemHotbar(Items.ENDER_PEARL)) != -1) {
            if (this.silent.getValue()) {
                InventoryUtil.silentSwitchToSlot(slot);
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                InventoryUtil.silentSwitchToSlot(this.originalSlot);
            } else {
                InventoryUtil.switchToSlot(slot);
                Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                InventoryUtil.switchToSlot(this.originalSlot);
            }
        }
    }
}

