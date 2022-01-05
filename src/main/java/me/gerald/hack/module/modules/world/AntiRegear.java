/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockShulkerBox
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Items
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.world;

import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.InventoryUtil;
import me.gerald.hack.util.MessageUtil;
import me.gerald.hack.util.WorldUtil;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiRegear
extends Module {
    public NumSetting range = this.register(new NumSetting("Range", 5.0f, 0.0f, 6.0f));
    public BoolSetting switchToPick = this.register(new BoolSetting("SwitchToPick", true));
    public int originalSlot = -1;
    public List<BlockPos> breakQueue = new ArrayList<BlockPos>();

    public AntiRegear() {
        super("AntiRegear", Module.Category.WORLD, "Breaks shulkers for you.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.breakQueue.isEmpty() && this.originalSlot != -1 && AntiRegear.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE && this.switchToPick.getValue()) {
            MessageUtil.sendClientMessage("Switching back to original slot.");
            InventoryUtil.switchToSlot(this.originalSlot);
            this.originalSlot = -1;
        }
        for (BlockPos pos : WorldUtil.getSphere(AntiRegear.mc.player.getPosition(), this.range.getValue(), false)) {
            if (!(AntiRegear.mc.world.getBlockState(pos).getBlock() instanceof BlockShulkerBox) || this.breakQueue.contains((Object)pos)) continue;
            this.breakQueue.add(pos);
        }
        if (!this.breakQueue.isEmpty()) {
            int pickSlot;
            if (AntiRegear.mc.player.getHeldItemMainhand().getItem() != Items.DIAMOND_PICKAXE && (pickSlot = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE)) != -1 && this.switchToPick.getValue()) {
                this.originalSlot = AntiRegear.mc.player.inventory.currentItem;
                InventoryUtil.switchToSlot(pickSlot);
            }
            for (BlockPos pos : this.breakQueue) {
                if (!(AntiRegear.mc.world.getBlockState(pos).getBlock() instanceof BlockShulkerBox) || AntiRegear.mc.player.getDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()) > (double)this.range.getValue()) {
                    this.breakQueue.remove((Object)pos);
                    return;
                }
                AntiRegear.mc.player.swingArm(EnumHand.MAIN_HAND);
                AntiRegear.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.getDirectionFromEntityLiving((BlockPos)pos, (EntityLivingBase)AntiRegear.mc.player)));
                AntiRegear.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.getDirectionFromEntityLiving((BlockPos)pos, (EntityLivingBase)AntiRegear.mc.player)));
            }
        }
    }
}

