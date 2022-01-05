/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.util.ChatAllowedCharacters
 *  org.lwjgl.input.Keyboard
 */
package me.gerald.hack.gui.comps.settings;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.gui.api.SettingComponent;
import me.gerald.hack.module.Module;
import me.gerald.hack.module.modules.client.ClickGui;
import me.gerald.hack.module.modules.client.Description;
import me.gerald.hack.setting.settings.StringSetting;
import me.gerald.hack.util.MessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class StringComponent
extends SettingComponent {
    public StringSetting setting;
    public Module module;
    public String typingString = "";
    public boolean typing = false;

    public StringComponent(Module module, StringSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height + 1), (int)new Color((int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getR(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getG(), (int)GeraldHack.INSTANCE.moduleManager.getModule(ClickGui.class).color.getB()).getRGB());
        Gui.drawRect((int)(this.x + 1), (int)this.y, (int)(this.x + this.width - 1), (int)(this.y + this.height), (int)(this.isInside(mouseX, mouseY) ? new Color(30, 30, 30, 240).getRGB() : new Color(15, 15, 15, 240).getRGB()));
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.setting.getName() + " " + (this.typing ? (Object)ChatFormatting.WHITE + this.typingString : (Object)ChatFormatting.GRAY + this.setting.getString()), (float)(this.x + 5), (float)(this.y + 2), this.module.isEnabled() ? -1 : (this.isInside(mouseX, mouseY) ? new Color(160, 160, 160).getRGB() : new Color(130, 130, 130).getRGB()));
        if (this.isInside(mouseX, mouseY)) {
            GeraldHack.INSTANCE.moduleManager.getModule(Description.class).setName("A string setting.");
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isInside(mouseX, mouseY) && mouseButton == 0) {
            this.typing = !this.typing;
            this.typingString = "";
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    @Override
    public void keyTyped(char keyChar, int key) throws UnsupportedFlavorException, IOException {
        if (this.typing) {
            switch (key) {
                case 14: {
                    this.typingString = this.removeLastLetter(this.typingString);
                    break;
                }
                case 28: {
                    if (this.typingString.length() > 0) {
                        this.setting.setString(this.typingString);
                    }
                    this.typing = false;
                    break;
                }
                case 47: {
                    if (!Keyboard.isKeyDown((int)29) && !Keyboard.isKeyDown((int)157)) break;
                    this.typingString = this.typingString + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    break;
                }
                case 46: {
                    if (!Keyboard.isKeyDown((int)29) && !Keyboard.isKeyDown((int)157)) break;
                    if (this.typingString.length() == 0) {
                        MessageUtil.sendErrorMessage("Nothing to copy.");
                        return;
                    }
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.typingString), null);
                    MessageUtil.sendClientMessage((Object)ChatFormatting.GRAY + "Copied text in string box to clipboard.");
                }
            }
            if (ChatAllowedCharacters.isAllowedCharacter((char)keyChar)) {
                this.typingString = this.typingString + keyChar;
            }
        }
    }

    public String removeLastLetter(String string) {
        String out = "";
        if (string != null && string.length() > 0) {
            out = string.substring(0, string.length() - 1);
        }
        return out;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}

