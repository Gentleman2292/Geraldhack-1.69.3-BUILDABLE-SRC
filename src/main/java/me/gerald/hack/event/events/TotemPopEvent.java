/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package me.gerald.hack.event.events;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TotemPopEvent
extends Event {
    public Entity entity;
    public int popCount;

    public TotemPopEvent(Entity entity, int popCount) {
        this.entity = entity;
        this.popCount = popCount;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public int getPopCount() {
        return this.popCount;
    }
}

