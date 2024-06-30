package com.scalyhat.pfac;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = pfac.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    
    private static final ForgeConfigSpec.ConfigValue<Integer> FISHE_SOUNDBITE_COOLDOWN = BUILDER
        .comment("Cooldown between soundbites for a single player, in milliseconds")
        .define("fishe_soundbite_cooldown", 2000);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> FISHE_STRINGS = BUILDER
        .comment("A list of \"fishes\" that can be chocolated.")
        .defineListAllowEmpty("fishe_strings", List.of("minecraft:cod", "minecraft:salmon", "minecraft:pufferfish", "minecraft:tropical_fish"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Set<Item> fisheItems = Set.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:cod")));
    public static Integer fisheSoundbiteCooldown;

    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        fisheItems = FISHE_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());

        fisheSoundbiteCooldown = FISHE_SOUNDBITE_COOLDOWN.get();
    }
}
