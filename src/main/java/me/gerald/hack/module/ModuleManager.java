/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.module;

import java.util.List;
import me.gerald.hack.module.Module;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.module.modules.client.Description;
import me.gerald.hack.module.modules.client.DiscordRPC;
import me.gerald.hack.module.modules.client.HUDEditor;
import me.gerald.hack.module.modules.client.Messages;
import me.gerald.hack.module.modules.combat.AutoTotem;
import me.gerald.hack.module.modules.combat.BowSpam;
import me.gerald.hack.module.modules.combat.Criticals;
import me.gerald.hack.module.modules.combat.CrystalAura;
import me.gerald.hack.module.modules.combat.HoleFill;
import me.gerald.hack.module.modules.combat.KillAura;
import me.gerald.hack.module.modules.combat.XPFast;
import me.gerald.hack.module.modules.hud.ArrayList;
import me.gerald.hack.module.modules.hud.CrystalCount;
import me.gerald.hack.module.modules.hud.GappleCount;
import me.gerald.hack.module.modules.hud.Name;
import me.gerald.hack.module.modules.hud.ObbyCount;
import me.gerald.hack.module.modules.hud.TextRadar;
import me.gerald.hack.module.modules.hud.TotemCount;
import me.gerald.hack.module.modules.hud.Watermark;
import me.gerald.hack.module.modules.hud.XPCount;
import me.gerald.hack.module.modules.misc.AntiNarrator;
import me.gerald.hack.module.modules.misc.AutoKit;
import me.gerald.hack.module.modules.misc.AutoLog;
import me.gerald.hack.module.modules.misc.Chat;
import me.gerald.hack.module.modules.misc.FakePlayer;
import me.gerald.hack.module.modules.misc.Spammer;
import me.gerald.hack.module.modules.movement.ReverseStep;
import me.gerald.hack.module.modules.movement.Sprint;
import me.gerald.hack.module.modules.movement.Step;
import me.gerald.hack.module.modules.movement.Velocity;
import me.gerald.hack.module.modules.player.PacketMine;
import me.gerald.hack.module.modules.player.Pearl;
import me.gerald.hack.module.modules.player.Respawn;
import me.gerald.hack.module.modules.player.Suicide;
import me.gerald.hack.module.modules.render.BurrowESP;
import me.gerald.hack.module.modules.render.ESP;
import me.gerald.hack.module.modules.render.ExplosionChams;
import me.gerald.hack.module.modules.render.FullBright;
import me.gerald.hack.module.modules.render.HoleESP;
import me.gerald.hack.module.modules.render.PopChams;
import me.gerald.hack.module.modules.render.StorageESP;
import me.gerald.hack.module.modules.render.VoidESP;
import me.gerald.hack.module.modules.world.AntiRegear;
import me.gerald.hack.module.modules.world.ShulkerLogger;
import me.gerald.hack.module.modules.world.TimeChanger;
import me.gerald.hack.module.modules.world.TotemPopCounter;
import me.gerald.hack.module.modules.world.VisualRange;

public class ModuleManager {
    public List<Module> modules = new java.util.ArrayList<Module>();
    public List<Module> sortedModules = new java.util.ArrayList<Module>();

    public ModuleManager() {
        this.modules.add(new Description());
        this.modules.add(new ClickGui());
        this.modules.add(new DiscordRPC());
        this.modules.add(new HUDEditor());
        this.modules.add(new Messages());
        this.modules.add(new AutoTotem());
        this.modules.add(new BowSpam());
        this.modules.add(new Criticals());
        this.modules.add(new CrystalAura());
        this.modules.add(new HoleFill());
        this.modules.add(new KillAura());
        this.modules.add(new XPFast());
        this.modules.add(new ArrayList());
        this.modules.add(new CrystalCount());
        this.modules.add(new GappleCount());
        this.modules.add(new Name());
        this.modules.add(new ObbyCount());
        this.modules.add(new TextRadar());
        this.modules.add(new TotemCount());
        this.modules.add(new Watermark());
        this.modules.add(new XPCount());
        this.modules.add(new AntiNarrator());
        this.modules.add(new AutoKit());
        this.modules.add(new AutoLog());
        this.modules.add(new Chat());
        this.modules.add(new FakePlayer());
        this.modules.add(new Spammer());
        this.modules.add(new ReverseStep());
        this.modules.add(new Sprint());
        this.modules.add(new Step());
        this.modules.add(new Velocity());
        this.modules.add(new Pearl());
        this.modules.add(new PacketMine());
        this.modules.add(new Respawn());
        this.modules.add(new Suicide());
        this.modules.add(new BurrowESP());
        this.modules.add(new ESP());
        this.modules.add(new ExplosionChams());
        this.modules.add(new FullBright());
        this.modules.add(new HoleESP());
        this.modules.add(new PopChams());
        this.modules.add(new StorageESP());
        this.modules.add(new VoidESP());
        this.modules.add(new AntiRegear());
        this.modules.add(new ShulkerLogger());
        this.modules.add(new TimeChanger());
        this.modules.add(new TotemPopCounter());
        this.modules.add(new VisualRange());
        this.modules.sort((arg_0, arg_1) -> this.sortABC(arg_0, arg_1));
        this.sortedModules.addAll(this.modules);
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public List<Module> getSortedModules() {
        return this.sortedModules;
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        for (Module module : this.getModules()) {
            if (module.getClass() != clazz) continue;
            return (T)module;
        }
        return null;
    }

    public Module getModuleByName(String name) {
        for (Module module : this.getModules()) {
            if (!module.getName().equalsIgnoreCase(name)) continue;
            return module;
        }
        return null;
    }

    public List<Module> getModulesByCategory(Module.Category category) {
        java.util.ArrayList<Module> modules = new java.util.ArrayList<Module>();
        for (Module module : this.getModules()) {
            if (module.getCategory() != category) continue;
            modules.add(module);
        }
        return modules;
    }

    private int sortABC(Module hack1, Module hack2) {
        return hack1.getName().compareTo(hack2.getName());
    }
}

