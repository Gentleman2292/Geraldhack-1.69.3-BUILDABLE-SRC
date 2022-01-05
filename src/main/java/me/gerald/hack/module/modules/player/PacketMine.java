/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$LeftClickBlock
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.opengl.GL11
 */
package me.gerald.hack.module.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Objects;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.ModeSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.NumberUtil;
import me.gerald.hack.util.RenderUtil;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

public class PacketMine
extends Module {
    public BoolSetting pickaxeOnly = this.register(new BoolSetting("PickaxeOnly", true));
    public ModeSetting renderMode = this.register(new ModeSetting("RenderMode", RenderMode.Both));
    public ColorSetting color = this.register(new ColorSetting("Color", 0.0f, 125.0f, 255.0f, 45.0f));
    public NumSetting lineWidth = this.register(new NumSetting("LineWidth", 2.0f, 0.0f, 4.0f));
    public BlockPos targetPos = null;
    public NumberUtil timeBreaking = new NumberUtil(0);

    public PacketMine() {
        super("PacketMine", Module.Category.PLAYER, "Will break blocks on click.");
    }

    @Override
    public String getMetaData() {
        return this.timeBreaking.getValue() != 0.0f ? "[" + (Object)ChatFormatting.GRAY + this.timeBreaking.getValue() + (Object)ChatFormatting.RESET + "]" : "";
    }

    @SubscribeEvent
    public void onClick(PlayerInteractEvent.LeftClickBlock event) {
        if (this.canBreak(event.getPos())) {
            if (this.pickaxeOnly.getValue() && PacketMine.mc.player.getHeldItemMainhand().getItem() != Items.DIAMOND_PICKAXE) {
                return;
            }
            PacketMine.mc.player.swingArm(EnumHand.MAIN_HAND);
            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), Objects.requireNonNull(event.getFace())));
            PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFace()));
            this.timeBreaking.increase(1.0f);
            this.targetPos = event.getPos();
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (this.targetPos != null) {
            AxisAlignedBB box = new AxisAlignedBB(this.targetPos).offset(-PacketMine.mc.getRenderManager().viewerPosX, -PacketMine.mc.getRenderManager().viewerPosY, -PacketMine.mc.getRenderManager().viewerPosZ);
            RenderUtil.prepare();
            GL11.glLineWidth((float)this.lineWidth.getValue());
            if (this.renderMode.getValueEnum() == RenderMode.Fill || this.renderMode.getValueEnum() == RenderMode.Both) {
                RenderGlobal.renderFilledBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)(this.color.getR() / 255.0f), (float)(this.color.getG() / 255.0f), (float)(this.color.getB() / 255.0f), (float)(this.color.getA() / 255.0f));
            }
            if (this.renderMode.getValueEnum() == RenderMode.Outline || this.renderMode.getValueEnum() == RenderMode.Both) {
                RenderGlobal.drawBoundingBox((double)box.minX, (double)box.minY, (double)box.minZ, (double)box.maxX, (double)box.maxY, (double)box.maxZ, (float)(this.color.getR() / 255.0f), (float)(this.color.getG() / 255.0f), (float)(this.color.getB() / 255.0f), (float)1.0f);
            }
            RenderUtil.release();
        }
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.targetPos != null && PacketMine.mc.world.getBlockState(this.targetPos).getBlock() == Blocks.AIR) {
            this.timeBreaking.zero();
            this.targetPos = null;
        }
    }

    @Override
    public void onDisable() {
        if (this.targetPos != null) {
            this.targetPos = null;
            this.timeBreaking.zero();
        }
    }

    public boolean canBreak(BlockPos pos) {
        if (PacketMine.mc.world.getBlockState(pos).getBlock() == Blocks.AIR) {
            return false;
        }
        return PacketMine.mc.world.getBlockState(pos).getBlockHardness((World)PacketMine.mc.world, pos) != -1.0f;
    }

    public static enum RenderMode {
        Fill,
        Outline,
        Both;

    }
}

