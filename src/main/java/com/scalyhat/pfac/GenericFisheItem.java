package com.scalyhat.pfac;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GenericFisheItem extends Item {
    
    public GenericFisheItem(Properties properties) {
        super(properties);
    }

    @Override
    public SoundEvent getEatingSound() {
        return pfac.FISHE_CHEWED.get();
    }

    @Override
    public boolean hurtEnemy(ItemStack item, LivingEntity defender, LivingEntity attacker) {
        Level world = attacker.level();
        if (!world.isClientSide) {FisheSound.playFisheSoundbiteAt(attacker);}
        return false;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        Level world = player.level();
        if (!world.isClientSide) {FisheSound.playFisheSoundbiteAt(player);}
        return true;
    }
}
