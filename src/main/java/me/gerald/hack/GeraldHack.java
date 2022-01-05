/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.Mod$Instance
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 */
package me.gerald.hack;

import java.io.IOException;
import me.gerald.hack.command.CommandManager;
import me.gerald.hack.event.EventListener;
import me.gerald.hack.event.listeners.KillListener;
import me.gerald.hack.gui.ClickGUI;
import me.gerald.hack.gui.HUD;
import me.gerald.hack.module.ModuleManager;
import me.gerald.hack.util.ConfigManager;
import me.gerald.hack.util.ShutdownHook;
import me.gerald.hack.util.friend.FriendManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="geraldhack", name="Gerald(Hack)", version="1.69.3")
public class GeraldHack {
    public static final String MOD_ID = "geraldhack";
    public static final String MOD_NAME = "Gerald(Hack)";
    public static final String VERSION = "1.69.3";
    public static String PREFIX = "-";
    @Mod.Instance(value="geraldhack")
    public static GeraldHack INSTANCE;
    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public ConfigManager configManager;
    public FriendManager friendManager;
    public EventListener eventListener;
    public KillListener killListener;
    public HUD hud;
    public ClickGUI gui;

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) throws IOException {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.moduleManager = new ModuleManager();
        this.commandManager = new CommandManager();
        this.configManager = new ConfigManager();
        this.friendManager = new FriendManager();
        this.eventListener = new EventListener();
        this.killListener = new KillListener();
        this.hud = new HUD();
        this.gui = new ClickGUI();
        this.configManager.load();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }

    public void setPrefix(String bind) {
        PREFIX = bind;
    }
}

