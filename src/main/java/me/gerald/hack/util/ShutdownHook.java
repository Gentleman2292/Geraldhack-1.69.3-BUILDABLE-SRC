/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.util;

import java.io.IOException;
import me.gerald.hack.GeraldHack;

public class ShutdownHook
extends Thread {
    @Override
    public void run() {
        try {
            GeraldHack.INSTANCE.configManager.save();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

