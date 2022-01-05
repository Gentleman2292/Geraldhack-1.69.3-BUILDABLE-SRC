/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package me.gerald.hack.module;

import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.event.events.ModuleToggleEvent;
import me.gerald.hack.gui.comps.ModuleComponent;
import me.gerald.hack.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class Module {
    private String name;
    private Category category;
    private String description;
    private int keybind;
    private boolean isEnabled = false;
    public boolean isVisible = true;
    private boolean needsKeybind = true;
    private ModuleComponent parent;
    public static Minecraft mc = Minecraft.getMinecraft();
    private List<Setting> settings = new ArrayList<Setting>();

    public Module(String name, Category category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.keybind = this.getName().equalsIgnoreCase("clickgui") ? 22 : 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public void setKeybind(int value) {
        this.keybind = value;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void toggle() {
        boolean bl = this.isEnabled = !this.isEnabled;
        if (this.isEnabled) {
            this.enable();
        } else {
            this.disable();
        }
    }

    public void onEnable() {
    }

    public void enable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        MinecraftForge.EVENT_BUS.post((Event)new ModuleToggleEvent.Pre(this));
        this.onEnable();
    }

    public void onDisable() {
    }

    public void disable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        MinecraftForge.EVENT_BUS.post((Event)new ModuleToggleEvent.Post(this));
        this.onDisable();
    }

    public <T extends Setting> T register(T setting) {
        this.settings.add(setting);
        return setting;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public boolean hasSetting() {
        return !this.getSettings().isEmpty();
    }

    public boolean needsKeybind() {
        return this.needsKeybind;
    }

    public void setNeedsKeybind(boolean value) {
        this.needsKeybind = value;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public String getMetaData() {
        return "";
    }

    public void setParent(ModuleComponent parent) {
        this.parent = parent;
    }

    public ModuleComponent getParent() {
        return this.parent;
    }

    public boolean nullCheck() {
        return mc == null || Module.mc.player == null || Module.mc.world == null;
    }

    public static enum Category {
        COMBAT,
        MOVEMENT,
        PLAYER,
        RENDER,
        WORLD,
        MISC,
        CLIENT,
        HUD,
        DESCRIPTION;

    }
}

