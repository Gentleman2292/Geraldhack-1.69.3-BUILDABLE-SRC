/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.gui.api;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public abstract class AbstractContainer {
    public int x;
    public int y;
    public int width;
    public int height;

    public AbstractContainer(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void drawScreen(int var1, int var2, float var3);

    public abstract void mouseClicked(int var1, int var2, int var3);

    public abstract void mouseReleased(int var1, int var2, int var3);

    public abstract void keyTyped(char var1, int var2) throws IOException, UnsupportedFlavorException;

    public abstract int getHeight();

    public boolean isInside(int mouseX, int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
}

