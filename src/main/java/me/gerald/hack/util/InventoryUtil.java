/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 */
package me.gerald.hack.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class InventoryUtil {
    public static int getItemHotbar(Item item) {
        for (int i = 0; i < 9; ++i) {
            if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem() != item) continue;
            return i;
        }
        return -1;
    }

    public static int getItemInventory(Item item, boolean hotbar) {
        int i;
        int n = i = hotbar ? 0 : 9;
        while (i < 36) {
            if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem() == item) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public static int getTotalAmountOfItem(Item item) {
        int amountOfItem = 0;
        for (int i = 0; i < 36; ++i) {
            ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (stack.getItem() != item) continue;
            amountOfItem += stack.getCount();
        }
        if (Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() == item) {
            amountOfItem += Minecraft.getMinecraft().player.getHeldItemOffhand().getCount();
        }
        return amountOfItem;
    }

    public static void moveItemToSlot(Integer startSlot, Integer endSlot) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, startSlot.intValue(), 0, ClickType.PICKUP, (EntityPlayer)Minecraft.getMinecraft().player);
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, endSlot.intValue(), 0, ClickType.PICKUP, (EntityPlayer)Minecraft.getMinecraft().player);
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, startSlot.intValue(), 0, ClickType.PICKUP, (EntityPlayer)Minecraft.getMinecraft().player);
    }

    public static void silentSwitchToSlot(int slot) {
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        Minecraft.getMinecraft().playerController.updateController();
    }

    public static void switchToSlot(int slot) {
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        Minecraft.getMinecraft().player.inventory.currentItem = slot;
        Minecraft.getMinecraft().playerController.updateController();
    }
}

