package com.scalyhat.pfac;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GenericFisheItem extends Item {
    
    public GenericFisheItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public SoundEvent getEatingSound() {
        return pfac.FISHE_CHEWED.get();
    }

    // @Override
    // public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity player) {
    //     ItemStack itemstack = super.finishUsingItem(stack, world, player);
    //     if (!world.isClientSide) {
    //         world.playSound(null, player.getX(), player.getY(), player.getZ(), 
    //             pfac.FISHE_CHEWED.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
    //     }
    //     return itemstack;
    // }
    
}
