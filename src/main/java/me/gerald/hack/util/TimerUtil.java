/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.util;

public class TimerUtil {
    private long time = -1L;
    long startTime = System.currentTimeMillis();
    long delay = 0L;
    boolean paused = false;

    public boolean passedMs(long ms) {
        return this.getMs(System.nanoTime() - this.time) >= ms;
    }

    public long getPassedTimeMs() {
        return this.getMs(System.nanoTime() - this.time);
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    public long getMs(long time) {
        return time / 1000000L;
    }

    public long getTime() {
        return this.time;
    }
}

