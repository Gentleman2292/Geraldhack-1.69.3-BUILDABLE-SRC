/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.gui.GuiGameOver
 *  net.minecraftforge.client.event.GuiOpenEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.gerald.hack.module.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.MessageUtil;
import me.gerald.hack.util.TimerUtil;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Respawn
extends Module {
    public NumSetting delay = this.register(new NumSetting("Delay", 0.0f, 0.0f, 1000.0f));
    public BoolSetting cancelScreen = this.register(new BoolSetting("CancelScreen", true));
    TimerUtil timer = new TimerUtil();
    String deathCoords;
    boolean hasAnnounced = false;

    public Respawn() {
        super("Respawn", Module.Category.PLAYER, "Respawns the player on death.");
    }

    @Override
    public String getMetaData() {
        return this.deathCoords != null ? "[" + this.deathCoords + (Object)ChatFormatting.RESET + "]" : "";
    }

    @SubscribeEvent
    public void onDeath(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver && this.timer.passedMs((long)this.delay.getValue())) {
            if (this.cancelScreen.getValue()) {
                event.setCanceled(true);
            }
            ChatFormatting formatting = ChatFormatting.GRAY;
            if (Respawn.mc.player.dimension == -1) {
                formatting = ChatFormatting.RED;
            } else if (Respawn.mc.player.dimension == 0) {
                formatting = ChatFormatting.GREEN;
            } else if (Respawn.mc.player.dimension == 1) {
                formatting = ChatFormatting.LIGHT_PURPLE;
            }
            this.deathCoords = (Object)ChatFormatting.GRAY + "X:" + (Object)formatting + Respawn.mc.player.getPosition().getX() + (Object)ChatFormatting.GRAY + " Y:" + (Object)formatting + Respawn.mc.player.getPosition().getY() + (Object)ChatFormatting.GRAY + " Z:" + (Object)formatting + Respawn.mc.player.getPosition().getZ();
            if (!this.hasAnnounced) {
                MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "You died at [" + this.deathCoords + (Object)ChatFormatting.GRAY + "]");
            }
            this.hasAnnounced = true;
            Respawn.mc.player.respawnPlayer();
            this.timer.reset();
            this.hasAnnounced = false;
        }
    }
}

