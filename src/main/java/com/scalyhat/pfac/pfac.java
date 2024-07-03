package com.scalyhat.pfac;

import java.util.Set;

import com.scalyhat.pfac.providers.DynamicLanguageProvider;
import com.scalyhat.pfac.providers.DynamicModelProvider;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
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
@Mod.EventBusSubscriber(modid = pfac.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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

    // public static final RegistryObject<Item> LE_COD_AU_CHOCOLAT = ITEMS.register("le_cod_au_chocolat", () ->
    //     new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.7f).build())));
    // public static final RegistryObject<Item> LE_SALMON_AU_CHOCOLAT = ITEMS.register("le_salmon_au_chocolat", () ->
    //     new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.8f).build())));
    // public static final RegistryObject<Item> LE_PUFFERFISH_AU_CHOCOLAT = ITEMS.register("le_pufferfish_au_chocolat", () ->
    //     new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(1f).effect(
    //         () -> new MobEffectInstance(MobEffects.POISON, 120, 3), 1).build())));
    // public static final RegistryObject<Item> LE_TROPICAL_FISH_AU_CHOCOLAT = ITEMS.register("le_tropical_fish_au_chocolat", () ->
    //     new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6f).build())));

    // public static final RegistryObject<Item> LE_PUFFERFISH_WITH_HANDLE = ITEMS.register("le_pufferfish_on_stick", () ->
    //     new FisheSwordItem(3, -3f, new Item.Properties().durability(64)));

    public static final RegistryObject<Item> LE_FISHE_GENERIQUE = ITEMS.register("le_fishe_au_chocolat", () ->
        new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().build())));

    public pfac()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // ITEMS.register(modEventBus);
        SOUNDS.register(modEventBus);
        ATTRIBUTES.register(modEventBus);
        ITEMS.register(modEventBus);

        modEventBus.addListener(this::addToTabs);
        modEventBus.addListener(this::modifyPlayers);

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        DynamicLanguageProvider languages = new DynamicLanguageProvider(
            output,
            MODID,
            "en-us"
        );

        DynamicModelProvider models = new DynamicModelProvider(
            output, 
            MODID, 
            event.getExistingFileHelper()
        );

        DatapackBuiltinEntriesProvider data = new DatapackBuiltinEntriesProvider(
            output,
            event.getLookupProvider(),
            new RegistrySetBuilder().add(Registries.ITEM, bootstrap -> {
                for (Item added : Config.forcedFishItems) {
                    FoodProperties currentFood = added.getFoodProperties(null, null);
                    Integer nutrition = currentFood == null ? 5 : currentFood.getNutrition();
                    Float saturationMod = currentFood == null ? 0.4f : currentFood.getSaturationModifier();

                    ResourceKey<Item> currentItem = ResourceKey.create(Registries.ITEM, new ResourceLocation(MODID, added.toString().concat("_au_chocolat")));
                    bootstrap.register(
                        currentItem, 
                        new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(nutrition)
                            .saturationMod(saturationMod)
                            .build()
                        )));
                }
            }),
            Set.of(MODID)
        );

        generator.addProvider(
            event.includeServer(), 
            languages
        );
        generator.addProvider(
            event.includeServer(), 
            models
        );
        generator.addProvider(
            event.includeServer(), 
            data
        );
    }

    @SubscribeEvent
    public void addToTabs(BuildCreativeModeTabContentsEvent event) {
        // if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
        //     event.accept(LE_COD_AU_CHOCOLAT);
        //     event.accept(LE_SALMON_AU_CHOCOLAT);
        //     event.accept(LE_TROPICAL_FISH_AU_CHOCOLAT);
        //     event.accept(LE_PUFFERFISH_AU_CHOCOLAT);
        // }
        // else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
        //     event.accept(LE_PUFFERFISH_WITH_HANDLE);
        // }
    }

    @SubscribeEvent
    public void modifyPlayers(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, CAN_PLAY_SOUNDBITE.get());
	}
}
