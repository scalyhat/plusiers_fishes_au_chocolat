package com.scalyhat.pfac;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// import java.util.Map;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(pfac.MODID)
public class pfac
{
    public static final String MODID = "pfac";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> LE_FISHE_SOUND = SOUNDS.register("le_fishe_au_chocolat", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("pfac:le_fishe_au_chocolat")));

    public static final RegistryObject<Item> LE_FISHE_AU_CHOCOLAT = ITEMS.register("le_fishe_au_chocolat", () ->
        new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.3F).build())));

    // public static final Map<String, RegistryObject<Item>> FISHES = Map<String, RegistryObject<Item>>();


    public pfac()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        SOUNDS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // public void makeFishe(String itemName) {
    //     ITEMS.register(itemName.concat("au_chocolat"), () -> 
    //         new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationMod(0.4F).build())
    //     );
    // }
    
    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(LE_FISHE_AU_CHOCOLAT);
        }
    }
}
