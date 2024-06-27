package com.scalyhat.pfac;

import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GenericFisheItem extends Item {
    
    public GenericFisheItem(Properties properties) {
        super(properties);
    }

    private void playFisheSoundbite(Level level, double X, double Y, double Z) {
        level.playSound(null, X, Y, Z, pfac.LE_FISHE_SOUNDBITE.get(), SoundSource.MASTER, 1.0F, 1.0F);
    }

    private void playFisheSoundbiteAt(LivingEntity entity) {
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (player.getAttributeValue(pfac.CAN_PLAY_SOUNDBITE.get()) == 0) {return;}

            PlayerSoundbiteCooldown cooldown = new PlayerSoundbiteCooldown(player);
            Timer cooldownTimer = new Timer("fisheCooldown".concat(player.getStringUUID()));
            cooldownTimer.schedule(cooldown, Config.fisheSoundbiteCooldown);
        }
        playFisheSoundbite(entity.level(), entity.getX(), entity.getY(), entity.getZ());
    }

    private class PlayerSoundbiteCooldown extends TimerTask {
        private final Player player;
        private final AttributeModifier disable = new AttributeModifier(getDescriptionId(), -1, AttributeModifier.Operation.ADDITION);
        
        public PlayerSoundbiteCooldown(Player player) {
            this.player = player;
            this.player.getAttribute(pfac.CAN_PLAY_SOUNDBITE.get()).addTransientModifier(disable);
        }

        @Override
        public void run() {
            this.player.getAttribute(pfac.CAN_PLAY_SOUNDBITE.get()).removeModifier(disable);
        }
    }

    @Override
    public SoundEvent getEatingSound() {
        return pfac.FISHE_CHEWED.get();
    }

    @Override
    public boolean hurtEnemy(ItemStack item, LivingEntity defender, LivingEntity attacker) {
        Level world = attacker.level();
        if (!world.isClientSide) {playFisheSoundbiteAt(attacker);}
        return false;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        Level world = player.level();
        if (!world.isClientSide) {playFisheSoundbiteAt(player);}
        return true;
    }
}
