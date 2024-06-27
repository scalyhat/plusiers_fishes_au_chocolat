package com.scalyhat.pfac;

import net.minecraft.client.gui.font.glyphs.BakedGlyph.Effect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.ai.attributes.Attribute;

// import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(pfac.MODID)
public class pfac
{
    public static final String MODID = "pfac";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);

    public static final RegistryObject<SoundEvent> LE_FISHE_SOUNDBITE = SOUNDS.register("le_fishe_au_chocolat", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "le_fishe_au_chocolat")));
    public static final RegistryObject<SoundEvent> FISHE_CHEWED = SOUNDS.register("fishe_chewed", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "fishe_chewed")));

    public static final RegistryObject<Attribute> CAN_PLAY_SOUNDBITE = ATTRIBUTES.register("fishe_sound_playable", () -> 
        new CustomAttribute("Whether this entity can play the Fishe soundbite", 1).setSyncable(true));

    public static final RegistryObject<Item> LE_COD_AU_CHOCOLAT = ITEMS.register("le_cod_au_chocolat", () ->
        new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationMod(0.5f).build())));
    public static final RegistryObject<Item> LE_SALMON_AU_CHOCOLAT = ITEMS.register("le_salmon_au_chocolat", () ->
        new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationMod(0.5f).build())));
    public static final RegistryObject<Item> LE_PUFFERFISH_AU_CHOCOLAT = ITEMS.register("le_pufferfish_au_chocolat", () ->
        new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).effect(
            () -> new MobEffectInstance(MobEffects.POISON, 120, 3), 1).build())));
    public static final RegistryObject<Item> LE_TROPICAL_FISH_AU_CHOCOLAT = ITEMS.register("le_tropical_fish_au_chocolat", () ->
        new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.4f).build())));

    public pfac()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        SOUNDS.register(modEventBus);
        ATTRIBUTES.register(modEventBus);

        modEventBus.addListener(this::addToTabs);
        modEventBus.addListener(this::modifyPlayers);

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(LE_COD_AU_CHOCOLAT);
            event.accept(LE_SALMON_AU_CHOCOLAT);
            event.accept(LE_TROPICAL_FISH_AU_CHOCOLAT);
            event.accept(LE_PUFFERFISH_AU_CHOCOLAT);
        }
    }

    @SubscribeEvent
    public void modifyPlayers(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, CAN_PLAY_SOUNDBITE.get());
	}
}
