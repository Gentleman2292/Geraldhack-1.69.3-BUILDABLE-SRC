/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 */
package me.gerald.hack.module.modules.misc;

import com.mojang.authlib.GameProfile;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.StringSetting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class FakePlayer
extends Module {
    public StringSetting name = this.register(new StringSetting("Name", "gerald0mc"));
    public EntityOtherPlayerMP fakePlayer = null;

    public FakePlayer() {
        super("FakePlayer", Module.Category.MISC, "Spawns in a fake player for testing.");
    }

    @Override
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        this.fakePlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(FakePlayer.mc.player.getUniqueID(), this.name.getString()));
        FakePlayer.mc.world.spawnEntity((Entity)this.fakePlayer);
        this.fakePlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
    }

    @Override
    public void onDisable() {
        if (this.fakePlayer != null) {
            FakePlayer.mc.world.removeEntity((Entity)this.fakePlayer);
        }
    }
}

