/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.gerald.hack.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.ModeSetting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Criticals
extends Module {
    public ModeSetting criticalMode = this.register(new ModeSetting("Mode", Mode.Packet));

    public Criticals() {
        super("Criticals", Module.Category.COMBAT, "Hits crits for you.");
    }

    @Override
    public String getMetaData() {
        return "[" + (Object)ChatFormatting.GRAY + this.criticalMode.getValueEnum().toString() + (Object)ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (Criticals.mc.player.isInWater() || Criticals.mc.player.isInLava()) {
            return;
        }
        if (Criticals.mc.player.onGround) {
            if (this.criticalMode.getValueEnum() == Mode.Packet) {
                Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.1625, Criticals.mc.player.posZ, false));
                Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
            } else {
                Criticals.mc.player.jump();
                if (this.criticalMode.getValueEnum() == Mode.MiniJump) {
                    Criticals.mc.player.motionY /= 2.0;
                }
            }
            Criticals.mc.player.onCriticalHit(event.getTarget());
        }
    }

    public static enum Mode {
        Packet,
        Jump,
        MiniJump;

    }
}

