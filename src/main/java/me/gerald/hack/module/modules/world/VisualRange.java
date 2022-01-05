/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$PlayerTickEvent
 */
package me.gerald.hack.module.modules.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.module.Module;
import me.gerald.hack.util.MessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VisualRange
extends Module {
    public List<EntityPlayer> loadedEntities = new ArrayList<EntityPlayer>();

    public VisualRange() {
        super("VisualRange", Module.Category.WORLD, "Says in chat when someone enters your visual range.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        for (Entity entity : VisualRange.mc.world.loadedEntityList) {
            EntityPlayer entityPlayer;
            if (entity == VisualRange.mc.player || !(entity instanceof EntityPlayer) || this.loadedEntities.contains((Object)(entityPlayer = (EntityPlayer)entity))) continue;
            MessageUtil.sendRemovableMessage((Object)ChatFormatting.GREEN + entityPlayer.getDisplayNameString() + (Object)ChatFormatting.GRAY + " has entered your visual range.", entity.getEntityId());
            this.loadedEntities.add(entityPlayer);
        }
        try {
            for (EntityPlayer entityPlayer : this.loadedEntities) {
                if (VisualRange.mc.world.loadedEntityList.contains((Object)entityPlayer)) continue;
                MessageUtil.sendRemovableMessage((Object)ChatFormatting.GREEN + entityPlayer.getDisplayName().getFormattedText() + (Object)ChatFormatting.GRAY + " has left your visual range.", entityPlayer.getEntityId());
                this.loadedEntities.add(entityPlayer);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

