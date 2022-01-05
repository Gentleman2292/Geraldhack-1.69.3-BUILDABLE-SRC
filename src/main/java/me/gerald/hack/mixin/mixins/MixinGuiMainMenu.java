/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiMainMenu
 *  net.minecraft.client.gui.GuiScreen
 */
package me.gerald.hack.mixin.mixins;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiMainMenu.class})
public class MixinGuiMainMenu
extends GuiScreen {
    @Inject(method={"drawScreen"}, at={@At(value="TAIL")})
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Gerald(Hack)" + (Object)ChatFormatting.GRAY + " v" + "1.69.3", 1.0f, 1.0f, -1);
    }
}

