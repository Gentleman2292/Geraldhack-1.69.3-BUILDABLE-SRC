/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.SPacketEntityStatus
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.ClientChatEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package me.gerald.hack.event;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Comparator;
import java.util.HashMap;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.command.Command;
import me.gerald.hack.event.events.PacketEvent;
import me.gerald.hack.event.events.PlayerDeathEvent;
import me.gerald.hack.event.events.TotemPopEvent;
import me.gerald.hack.gui.ClickGUI;
import me.gerald.hack.module.Module;
import me.gerald.hack.module.modules.client.Description;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventListener {
    public HashMap<Entity, Integer> popMap = new HashMap();

    public EventListener() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            int key = Keyboard.getEventKey();
            for (Module module : GeraldHack.INSTANCE.moduleManager.getModules()) {
                if (module.getKeybind() != key) continue;
                System.out.println("Attempting to toggle a module called " + (Object)ChatFormatting.GREEN + module.getName());
                module.toggle();
            }
        }
    }

    @SubscribeEvent
    public void onChatSend(ClientChatEvent event) {
        String[] args = event.getMessage().split(" ");
        if (event.getMessage().startsWith(GeraldHack.PREFIX)) {
            event.setCanceled(true);
            for (Command command : GeraldHack.INSTANCE.commandManager.getCommands()) {
                if (!args[0].equalsIgnoreCase(GeraldHack.PREFIX + command.getName())) continue;
                command.onCommand(args);
            }
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        SPacketEntityStatus packet;
        if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).getOpCode() == 35) {
            if (!this.popMap.containsKey((Object)packet.getEntity((World)Minecraft.getMinecraft().world))) {
                MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(packet.getEntity((World)Minecraft.getMinecraft().world), 1));
                this.popMap.put(packet.getEntity((World)Minecraft.getMinecraft().world), 1);
            } else {
                this.popMap.put(packet.getEntity((World)Minecraft.getMinecraft().world), this.popMap.get((Object)packet.getEntity((World)Minecraft.getMinecraft().world)) + 1);
                MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(packet.getEntity((World)Minecraft.getMinecraft().world), this.popMap.get((Object)packet.getEntity((World)Minecraft.getMinecraft().world))));
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null) {
            for (EntityPlayer player : Minecraft.getMinecraft().world.playerEntities) {
                if (!player.isDead && player.isEntityAlive() && !(player.getHealth() <= 0.0f)) continue;
                MinecraftForge.EVENT_BUS.post((Event)new PlayerDeathEvent((Entity)player));
                if (!this.popMap.containsKey((Object)player)) continue;
                this.popMap.remove((Object)player);
            }
            GeraldHack.INSTANCE.moduleManager.sortedModules.sort(Comparator.comparing(module -> -Minecraft.getMinecraft().fontRenderer.getStringWidth(module.getName() + " " + module.getMetaData())));
            if (Minecraft.getMinecraft().currentScreen instanceof ClickGUI) {
                if (Minecraft.getMinecraft().fontRenderer.getStringWidth(GeraldHack.INSTANCE.moduleManager.getModule(Description.class).getName()) > 125) {
                    GeraldHack.INSTANCE.moduleManager.getModule(Description.class).getParent().parent.width = Minecraft.getMinecraft().fontRenderer.getStringWidth(GeraldHack.INSTANCE.moduleManager.getModule(Description.class).getName()) + 3;
                    GeraldHack.INSTANCE.moduleManager.getModule(Description.class).getParent().width = Minecraft.getMinecraft().fontRenderer.getStringWidth(GeraldHack.INSTANCE.moduleManager.getModule(Description.class).getName()) + 3;
                } else {
                    GeraldHack.INSTANCE.moduleManager.getModule(Description.class).getParent().parent.width = 125;
                    GeraldHack.INSTANCE.moduleManager.getModule(Description.class).getParent().width = 125;
                }
            }
        }
    }
}

