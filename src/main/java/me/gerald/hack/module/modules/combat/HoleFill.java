/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.combat;

import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.InventoryUtil;
import me.gerald.hack.util.MessageUtil;
import me.gerald.hack.util.TimerUtil;
import me.gerald.hack.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HoleFill
extends Module {
    public NumSetting range = this.register(new NumSetting("Range", 4.0f, 0.0f, 6.0f));
    public NumSetting delay = this.register(new NumSetting("Delay", 50.0f, 0.0f, 250.0f));
    public TimerUtil timer = new TimerUtil();

    public HoleFill() {
        super("HoleFill", Module.Category.COMBAT, "Fills holes.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        int obsidianSlot = InventoryUtil.getItemHotbar(Item.getItemFromBlock((Block)Blocks.OBSIDIAN));
        if (obsidianSlot == -1) {
            MessageUtil.sendErrorMessage("No obsidian in hotbar toggling.");
            this.toggle();
            return;
        }
        for (BlockPos pos : WorldUtil.getSphere(HoleFill.mc.player.getPosition(), this.range.getValue(), false)) {
            EnumFacing[] enumFacings;
            if (!this.isHole(pos)) continue;
            if (HoleFill.mc.player.inventory.currentItem != obsidianSlot) {
                InventoryUtil.switchToSlot(obsidianSlot);
            }
            for (EnumFacing side : enumFacings = EnumFacing.values()) {
                BlockPos neighbor = pos.offset(side);
                EnumFacing side2 = side.getOpposite();
                Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (!this.timer.passedMs((long)this.delay.getValue())) continue;
                HoleFill.mc.player.swingArm(EnumHand.MAIN_HAND);
                HoleFill.mc.playerController.processRightClickBlock(HoleFill.mc.player, HoleFill.mc.world, pos, side2, hitVec, EnumHand.MAIN_HAND);
                this.timer.reset();
            }
        }
    }

    public boolean isHole(BlockPos pos) {
        return !(HoleFill.mc.world.getBlockState(pos).getBlock() != Blocks.AIR || HoleFill.mc.world.getBlockState(pos.down()).getBlock() != Blocks.BEDROCK && HoleFill.mc.world.getBlockState(pos.down()).getBlock() != Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(pos.up()).getBlock() != Blocks.AIR || HoleFill.mc.world.getBlockState(pos.up().up()).getBlock() != Blocks.AIR || HoleFill.mc.world.getBlockState(pos.north()).getBlock() != Blocks.BEDROCK && HoleFill.mc.world.getBlockState(pos.north()).getBlock() != Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(pos.south()).getBlock() != Blocks.BEDROCK && HoleFill.mc.world.getBlockState(pos.south()).getBlock() != Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(pos.west()).getBlock() != Blocks.BEDROCK && HoleFill.mc.world.getBlockState(pos.west()).getBlock() != Blocks.OBSIDIAN || HoleFill.mc.world.getBlockState(pos.east()).getBlock() != Blocks.BEDROCK && HoleFill.mc.world.getBlockState(pos.east()).getBlock() != Blocks.OBSIDIAN);
    }
}

