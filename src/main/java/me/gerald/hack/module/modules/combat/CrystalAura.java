/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.init.MobEffects
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 */
package me.gerald.hack.module.modules.combat;

import me.gerald.hack.module.Module;
import me.gerald.hack.setting.settings.BoolSetting;
import me.gerald.hack.setting.settings.NumSetting;
import me.gerald.hack.util.InventoryUtil;
import me.gerald.hack.util.TimerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CrystalAura
extends Module {
    public NumSetting range = this.register(new NumSetting("Range", 4.0f, 1.0f, 6.0f));
    public NumSetting delayMS = this.register(new NumSetting("DelayMS", 50.0f, 0.0f, 500.0f));
    public BoolSetting antiWeakness = this.register(new BoolSetting("AntiWeakness", true));
    public TimerUtil timer = new TimerUtil();

    public CrystalAura() {
        super("CrystalAura", Module.Category.COMBAT, "Automatically breaks crsytals in range.");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        for (Entity entity : CrystalAura.mc.world.loadedEntityList) {
            int swordSlot;
            if (!(CrystalAura.mc.player.getDistance(entity) <= this.range.getValue()) || !(entity instanceof EntityEnderCrystal) || !this.timer.passedMs((long)this.delayMS.getValue())) continue;
            int originalSlot = -1;
            if (this.antiWeakness.getValue() && CrystalAura.mc.player.isPotionActive(MobEffects.WEAKNESS) && (swordSlot = InventoryUtil.getItemHotbar(Items.DIAMOND_SWORD)) != -1) {
                originalSlot = CrystalAura.mc.player.inventory.currentItem;
                InventoryUtil.switchToSlot(swordSlot);
            }
            CrystalAura.mc.playerController.attackEntity((EntityPlayer)CrystalAura.mc.player, entity);
            if (originalSlot != -1) {
                InventoryUtil.switchToSlot(originalSlot);
            }
            this.timer.reset();
        }
    }
}

