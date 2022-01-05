/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 */
package me.gerald.hack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.command.Command;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.Setting;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ModeSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.setting.settings.StringSetting;
import me.gerald.hack.util.MessageUtil;

public class SetSetting
extends Command {
    public SetSetting() {
        super("SetSetting", "Sets a modules setting value.", new String[]{"setsetting", "[module]", "[setting]", "[value]"});
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length < 2) {
            MessageUtil.sendErrorMessage("Please specify a module.");
            return;
        }
        if (args.length < 3) {
            MessageUtil.sendErrorMessage("Please specify a setting.");
            return;
        }
        if (args.length < 4) {
            MessageUtil.sendErrorMessage("Please specify value you wish to set setting to.");
            return;
        }
        String moduleName = args[1];
        String settingName = args[2];
        String settingValue = args[3];
        for (Module module : GeraldHack.INSTANCE.moduleManager.getModules()) {
            if (!module.getName().equalsIgnoreCase(moduleName)) continue;
            for (Setting setting : module.getSettings()) {
                if (!setting.getName().equalsIgnoreCase(settingName)) continue;
                if (setting instanceof BoolSetting) {
                    if (((BoolSetting)setting).value == Boolean.parseBoolean(settingValue)) {
                        MessageUtil.sendErrorMessage("Value of " + settingName + " is already " + Boolean.parseBoolean(settingValue));
                        return;
                    }
                    ((BoolSetting)setting).value = Boolean.parseBoolean(settingValue);
                    MessageUtil.sendClientMessage("Set " + (Object)ChatFormatting.GRAY + setting.getName() + (Object)ChatFormatting.RESET + " to " + (Object)ChatFormatting.GREEN + Boolean.parseBoolean(settingValue));
                    continue;
                }
                if (setting instanceof NumSetting) {
                    if (Float.parseFloat(settingValue) > ((NumSetting)setting).getMax() || Float.parseFloat(settingValue) < ((NumSetting)setting).getMin()) {
                        MessageUtil.sendErrorMessage("Value you have put is smaller/larger than the settings min/max please try again.");
                        return;
                    }
                    if (((NumSetting)setting).getValue() == Float.parseFloat(settingValue)) {
                        MessageUtil.sendErrorMessage("Value you have put is already what the setting is set to.");
                        return;
                    }
                    ((NumSetting)setting).setValue(Float.parseFloat(settingValue));
                    MessageUtil.sendClientMessage("Set " + (Object)ChatFormatting.GRAY + setting.getName() + (Object)ChatFormatting.RESET + " to " + (Object)ChatFormatting.GREEN + settingValue);
                    continue;
                }
                if (setting instanceof ModeSetting) {
                    if (((ModeSetting)setting).getValueIndex() == Integer.parseInt(settingValue)) {
                        MessageUtil.sendErrorMessage("Value of " + settingName + " is already " + Integer.parseInt(settingValue));
                        return;
                    }
                    ((ModeSetting)setting).setValueIndex(Integer.parseInt(settingValue));
                    MessageUtil.sendClientMessage("Set " + (Object)ChatFormatting.GRAY + setting.getName() + (Object)ChatFormatting.RESET + " to " + (Object)ChatFormatting.GREEN + ((ModeSetting)setting).getValueEnum().toString());
                    continue;
                }
                if (!(setting instanceof StringSetting)) continue;
                if (((StringSetting)setting).getString().equalsIgnoreCase(settingValue)) {
                    MessageUtil.sendErrorMessage("Value of " + settingName + " is already " + settingValue);
                    return;
                }
                ((StringSetting)setting).setString(settingValue);
                MessageUtil.sendClientMessage("Set " + (Object)ChatFormatting.GRAY + setting.getName() + (Object)ChatFormatting.RESET + " to " + (Object)ChatFormatting.GREEN + settingValue);
            }
        }
    }
}

