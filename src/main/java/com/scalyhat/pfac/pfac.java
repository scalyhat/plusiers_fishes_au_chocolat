package com.scalyhat.pfac;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(pfac.MODID)
public class pfac
{
    public static final String MODID = "pfac";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> LE_FISHE_SOUNDBITE = SOUNDS.register("le_fishe_au_chocolat", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "le_fishe_au_chocolat")));
    public static final RegistryObject<SoundEvent> FISHE_CHEWED = SOUNDS.register("fishe_chewed", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "fishe_chewed")));

    public static final RegistryObject<Item> LE_FISHE_AU_CHOCOLAT = ITEMS.register("le_fishe_au_chocolat", () ->
        new GenericFisheItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.3F).build())));

    // public static final Map<String, RegistryObject<Item>> FISHES = Map<String, RegistryObject<Item>>();


    public pfac()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        SOUNDS.register(modEventBus);
        modEventBus.addListener(this::addToTabs);

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // public void makeFishe(String itemName) {
    //     ITEMS.register(itemName.concat("au_chocolat"), () -> 
    //         new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationMod(0.4F).build())
    //     );
    // }
    
    @SubscribeEvent
    public void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(LE_FISHE_AU_CHOCOLAT);
        }
    }

    @SubscribeEvent
    public void fisheOnToss(ItemTossEvent event) {
        if (event.getEntity().getItem().getItem() instanceof GenericFisheItem) {
            final Level eventWorld = event.getEntity().level();
            final Entity item = event.getEntity();
            if (!eventWorld.isClientSide) {
                eventWorld.playSound(null, item.getX(), item.getY(), item.getZ(), 
                    pfac.LE_FISHE_SOUNDBITE.get(), SoundSource.MASTER, 1.0F, 1.0F);
            }
        }
    }
}
