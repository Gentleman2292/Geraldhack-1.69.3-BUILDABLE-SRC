/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module;

import me.gerald.hack.gui.api.HUDComponent;
import me.gerald.hack.module.Module;

public class HUDModule
extends Module {
    public final HUDComponent component;

    public HUDModule(HUDComponent component, String name, Module.Category category, String description) {
        super(name, category, description);
        this.component = component;
    }

    public HUDComponent getComponent() {
        return this.component;
    }
}

