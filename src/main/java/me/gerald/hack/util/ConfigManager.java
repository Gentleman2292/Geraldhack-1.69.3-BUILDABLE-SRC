/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 *  org.lwjgl.input.Keyboard
 */
package me.gerald.hack.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import me.gerald.hack.GeraldHack;
import me.gerald.hack.module.Module;
import me.gerald.hack.setting.Setting;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.ColorSetting;
import me.gerald.hack.setting.settings.ModeSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.setting.settings.StringSetting;
import org.lwjgl.input.Keyboard;

public class ConfigManager {
    public void save() throws IOException {
        this.registerFolders();
        for (Module module : GeraldHack.INSTANCE.moduleManager.getModules()) {
            if (module.getDescription().equalsIgnoreCase("DESCRIPTION")) continue;
            if (Files.exists(Paths.get("Gerald(Hack)/Modules/" + module.getName() + ".json", new String[0]), new LinkOption[0])) {
                new File("Gerald(Hack)/Modules/" + module.getName() + ".json").delete();
            }
            Files.createFile(Paths.get("Gerald(Hack)/Modules/" + module.getName() + ".json", new String[0]), new FileAttribute[0]);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            OutputStreamWriter stream = new OutputStreamWriter((OutputStream)new FileOutputStream("Gerald(Hack)/Modules/" + module.getName() + ".json"), StandardCharsets.UTF_8);
            JsonObject moduleObject = new JsonObject();
            JsonObject settingObject = new JsonObject();
            moduleObject.add("Module", (JsonElement)new JsonPrimitive(module.getName()));
            for (Setting setting : module.getSettings()) {
                if (setting instanceof BoolSetting) {
                    settingObject.add(setting.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(((BoolSetting)setting).getValue())));
                    continue;
                }
                if (setting instanceof NumSetting) {
                    settingObject.add(setting.getName(), (JsonElement)new JsonPrimitive((Number)Float.valueOf(((NumSetting)setting).getValue())));
                    continue;
                }
                if (setting instanceof ModeSetting) {
                    settingObject.add(setting.getName(), (JsonElement)new JsonPrimitive((Number)((ModeSetting)setting).getValueIndex()));
                    continue;
                }
                if (setting instanceof ColorSetting) {
                    JsonObject colorObject = new JsonObject();
                    colorObject.add("Red", (JsonElement)new JsonPrimitive((Number)Float.valueOf(((ColorSetting)setting).getR())));
                    colorObject.add("Green", (JsonElement)new JsonPrimitive((Number)Float.valueOf(((ColorSetting)setting).getG())));
                    colorObject.add("Blue", (JsonElement)new JsonPrimitive((Number)Float.valueOf(((ColorSetting)setting).getB())));
                    colorObject.add("Alpha", (JsonElement)new JsonPrimitive((Number)Float.valueOf(((ColorSetting)setting).getA())));
                    settingObject.add(setting.getName(), (JsonElement)colorObject);
                    continue;
                }
                if (!(setting instanceof StringSetting)) continue;
                settingObject.add(setting.getName(), (JsonElement)new JsonPrimitive(((StringSetting)setting).getString()));
            }
            moduleObject.add("Bind", (JsonElement)new JsonPrimitive(Keyboard.getKeyName((int)module.getKeybind())));
            moduleObject.add("Enabled", (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isEnabled())));
            moduleObject.add("Visible", (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isVisible)));
            moduleObject.add("Settings", (JsonElement)settingObject);
            stream.write(gson.toJson(new JsonParser().parse(moduleObject.toString())));
            stream.close();
        }
        if (Files.exists(Paths.get("Gerald(Hack)/Client/Friends.json", new String[0]), new LinkOption[0])) {
            // empty if block
        }
        new File("Gerald(Hack)/Client/Friends.json").delete();
        Files.createFile(Paths.get("Gerald(Hack)/Client/Friends.json", new String[0]), new FileAttribute[0]);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter stream = new OutputStreamWriter((OutputStream)new FileOutputStream("Gerald(Hack)/Client/Friends.json"), StandardCharsets.UTF_8);
        stream.write(gson.toJson(GeraldHack.INSTANCE.friendManager.getFriends()));
        stream.close();
    }

    public void saveModule(Module module) {
        if (module.getDescription().equalsIgnoreCase("DESCRIPTION")) {
            return;
        }
        try {
            this.registerFolders();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (Files.exists(Paths.get("Gerald(Hack)/Modules/" + module.getName() + ".json", new String[0]), new LinkOption[0])) {
            new File("Gerald(Hack)/Modules/" + module.getName() + ".json").delete();
        }
        try {
            Files.createFile(Paths.get("Gerald(Hack)/Modules/" + module.getName() + ".json", new String[0]), new FileAttribute[0]);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter stream = null;
        try {
            stream = new OutputStreamWriter((OutputStream)new FileOutputStream("Gerald(Hack)/Modules/" + module.getName() + ".json"), StandardCharsets.UTF_8);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonObject moduleObject = new JsonObject();
        JsonObject settingObject = new JsonObject();
        moduleObject.add("Module", (JsonElement)new JsonPrimitive(module.getName()));
        for (Setting setting : module.getSettings()) {
            if (setting instanceof BoolSetting) {
                settingObject.add(setting.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(((BoolSetting)setting).getValue())));
                continue;
            }
            if (setting instanceof NumSetting) {
                settingObject.add(setting.getName(), (JsonElement)new JsonPrimitive((Number)Float.valueOf(((NumSetting)setting).getValue())));
                continue;
            }
            if (setting instanceof ModeSetting) {
                settingObject.add(setting.getName(), (JsonElement)new JsonPrimitive((Number)((ModeSetting)setting).getValueIndex()));
                continue;
            }
            if (setting instanceof ColorSetting) {
                JsonObject colorObject = new JsonObject();
                colorObject.add("Red", (JsonElement)new JsonPrimitive((Number)Float.valueOf(((ColorSetting)setting).getR())));
                colorObject.add("Green", (JsonElement)new JsonPrimitive((Number)Float.valueOf(((ColorSetting)setting).getG())));
                colorObject.add("Blue", (JsonElement)new JsonPrimitive((Number)Float.valueOf(((ColorSetting)setting).getB())));
                colorObject.add("Alpha", (JsonElement)new JsonPrimitive((Number)Float.valueOf(((ColorSetting)setting).getA())));
                settingObject.add(setting.getName(), (JsonElement)colorObject);
                continue;
            }
            if (!(setting instanceof StringSetting)) continue;
            settingObject.add(setting.getName(), (JsonElement)new JsonPrimitive(((StringSetting)setting).getString()));
        }
        moduleObject.add("Bind", (JsonElement)new JsonPrimitive(Keyboard.getKeyName((int)module.getKeybind())));
        moduleObject.add("Enabled", (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isEnabled())));
        moduleObject.add("Visible", (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isVisible)));
        moduleObject.add("Settings", (JsonElement)settingObject);
        try {
            stream.write(gson.toJson(new JsonParser().parse(moduleObject.toString())));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() throws IOException {
        for (Module module : GeraldHack.INSTANCE.moduleManager.getModules()) {
            JsonElement visibleElement;
            String visibleS;
            if (module.getDescription().equalsIgnoreCase("DESCRIPTION")) continue;
            System.out.println("Attempting to load config for. (" + module.getName() + ")");
            if (!Files.exists(Paths.get("Gerald(Hack)/Modules/" + module.getName() + ".json", new String[0]), new LinkOption[0])) {
                return;
            }
            InputStream stream = Files.newInputStream(Paths.get("Gerald(Hack)/Modules/" + module.getName() + ".json", new String[0]), new OpenOption[0]);
            JsonObject moduleObject = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
            if (moduleObject.get("Module") == null) {
                return;
            }
            JsonObject settingObject = moduleObject.get("Settings").getAsJsonObject();
            for (Setting setting : module.getSettings()) {
                JsonElement settingDataObject = settingObject.get(setting.getName());
                try {
                    if (settingDataObject == null || !settingDataObject.isJsonPrimitive() && !settingDataObject.isJsonObject()) continue;
                    if (setting instanceof BoolSetting) {
                        ((BoolSetting)setting).setValue(settingDataObject.getAsBoolean());
                        continue;
                    }
                    if (setting instanceof NumSetting) {
                        ((NumSetting)setting).setValue(settingDataObject.getAsFloat());
                        continue;
                    }
                    if (setting instanceof ModeSetting) {
                        ((ModeSetting)setting).setValueIndex(settingDataObject.getAsInt());
                        continue;
                    }
                    if (setting instanceof ColorSetting) {
                        JsonObject colorObject = settingObject.get(setting.getName()).getAsJsonObject();
                        JsonElement redElement = colorObject.get("Red");
                        JsonElement greenElement = colorObject.get("Green");
                        JsonElement blueElement = colorObject.get("Blue");
                        JsonElement alphaElement = colorObject.get("Alpha");
                        try {
                            ((ColorSetting)setting).setR(redElement.getAsInt());
                            ((ColorSetting)setting).setG(greenElement.getAsInt());
                            ((ColorSetting)setting).setB(blueElement.getAsInt());
                            ((ColorSetting)setting).setA(alphaElement.getAsInt());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    if (!(setting instanceof StringSetting)) continue;
                    ((StringSetting)setting).setString(settingDataObject.getAsString());
                }
                catch (Exception e) {
                    System.out.println("Faulty setting found (" + module.getName() + ", " + setting.getName() + ")");
                    e.printStackTrace();
                }
            }
            JsonElement bindElement = moduleObject.get("Bind");
            String bindS = bindElement.getAsString();
            System.out.println(bindS);
            try {
                module.setKeybind(Keyboard.getKeyIndex((String)bindS));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            JsonElement enabledElement = moduleObject.get("Enabled");
            String enabledS = enabledElement.getAsString();
            if (enabledS.contains("true")) {
                try {
                    module.toggle();
                    System.out.println("Toggled (" + module.getName() + ")");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if ((visibleS = (visibleElement = moduleObject.get("Visible")).getAsString()).contains("false")) {
                try {
                    module.setVisible(false);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Loaded config for module. (" + module.getName() + ")");
            stream.close();
        }
    }

    public void registerFolders() throws IOException {
        if (!Files.exists(Paths.get("Gerald(Hack)/", new String[0]), new LinkOption[0])) {
            Files.createDirectories(Paths.get("Gerald(Hack)/", new String[0]), new FileAttribute[0]);
        }
        if (!Files.exists(Paths.get("Gerald(Hack)/Modules/", new String[0]), new LinkOption[0])) {
            Files.createDirectories(Paths.get("Gerald(Hack)/Modules/", new String[0]), new FileAttribute[0]);
        }
        if (!Files.exists(Paths.get("Gerald(Hack)/Client/", new String[0]), new LinkOption[0])) {
            Files.createDirectories(Paths.get("Gerald(Hack)/Client/", new String[0]), new FileAttribute[0]);
        }
    }
}

