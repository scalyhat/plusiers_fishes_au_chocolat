package com.scalyhat.pfac;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public class FisheSwordItem extends SwordItem {

    public FisheSwordItem(int damage, float speed, Item.Properties properties) {
        super(Tiers.WOOD, damage, speed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack item, LivingEntity defender, LivingEntity attacker) {
        Level world = attacker.level();
        if (!world.isClientSide) {FisheSound.playFisheSoundbiteAt(attacker);}
        defender.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
        return super.hurtEnemy(item, defender, attacker);
    }
}
