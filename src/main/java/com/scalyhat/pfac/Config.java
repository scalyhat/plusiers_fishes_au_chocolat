package com.scalyhat.pfac;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import java.util.Set;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = pfac.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    
    private static final ForgeConfigSpec.ConfigValue<Integer> FISHE_SOUNDBITE_COOLDOWN = BUILDER
        .comment("Cooldown between soundbites for a single player, in milliseconds")
        .define("fishe_soundbite_cooldown", 2000);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Set<Item> fisheItems;
    public static Integer fisheSoundbiteCooldown;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        fisheSoundbiteCooldown = FISHE_SOUNDBITE_COOLDOWN.get();
    }
}
