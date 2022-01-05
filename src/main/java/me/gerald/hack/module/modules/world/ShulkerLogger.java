/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityShulkerBox
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.module.Module;
import me.gerald.hack.util.MessageUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ShulkerLogger
extends Module {
    public List<TileEntityShulkerBox> shulkerBoxes = new ArrayList<TileEntityShulkerBox>();

    public ShulkerLogger() {
        super("ShulkerLogger", Module.Category.WORLD, "Sends a name and location of shulker box.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        for (TileEntity entity : ShulkerLogger.mc.world.loadedTileEntityList) {
            TileEntityShulkerBox shulkerBox;
            if (!(entity instanceof TileEntityShulkerBox) || this.shulkerBoxes.contains((Object)(shulkerBox = (TileEntityShulkerBox)entity))) continue;
            MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Located shulker box named " + (Object)ChatFormatting.AQUA + shulkerBox.getName() + (Object)ChatFormatting.GRAY + " and is at [X:" + (Object)ChatFormatting.AQUA + shulkerBox.getPos().getX() + (Object)ChatFormatting.GRAY + " Y:" + (Object)ChatFormatting.AQUA + shulkerBox.getPos().getY() + (Object)ChatFormatting.GRAY + " Z:" + (Object)ChatFormatting.AQUA + shulkerBox.getPos().getZ() + (Object)ChatFormatting.GRAY + "]");
            this.shulkerBoxes.add(shulkerBox);
        }
    }
}

