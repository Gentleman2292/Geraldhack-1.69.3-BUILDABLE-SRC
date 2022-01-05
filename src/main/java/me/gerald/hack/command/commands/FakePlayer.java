/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 */
package me.gerald.hack.command.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.command.Command;
import me.gerald.hack.util.MessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class FakePlayer
extends Command {
    public EntityOtherPlayerMP fakePlayer = null;

    public FakePlayer() {
        super("FakePlayer", "Spawns a fake entity into the world.", new String[]{"fakeplayer", "[name]"});
    }

    @Override
    public void onCommand(String[] args) {
        if (this.fakePlayer == null) {
            String fakePlayerName = "Gerald(Hack)";
            if (args.length == 2) {
                fakePlayerName = args[1];
            } else {
                MessageUtil.sendClientMessage("If you want to name your fake player next time do [prefix]fakeplayer [name].");
            }
            this.fakePlayer = new EntityOtherPlayerMP((World)Minecraft.getMinecraft().world, new GameProfile(Minecraft.getMinecraft().player.getUniqueID(), fakePlayerName));
            Minecraft.getMinecraft().world.spawnEntity((Entity)this.fakePlayer);
            this.fakePlayer.copyLocationAndAnglesFrom((Entity)Minecraft.getMinecraft().player);
            MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Spawned fake player named " + (Object)ChatFormatting.AQUA + fakePlayerName + (Object)ChatFormatting.GRAY + ".");
        } else {
            Minecraft.getMinecraft().world.removeEntity((Entity)this.fakePlayer);
            MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Removed fake player.");
            this.fakePlayer = null;
        }
    }
}

