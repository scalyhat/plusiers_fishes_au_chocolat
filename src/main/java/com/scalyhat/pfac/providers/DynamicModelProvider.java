package com.scalyhat.pfac.providers;

import com.scalyhat.pfac.Config;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class DynamicModelProvider extends ItemModelProvider {

    public DynamicModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    private final ResourceLocation chocolateTexture = new ResourceLocation("pfac:item/long_au_chocolat");

    public ItemModelBuilder chocolateItem(Item item) {
        ResourceLocation itemKey = ForgeRegistries.ITEMS.getKey(item);

        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(itemKey.getNamespace(), "item/" + itemKey.getPath()))
                .texture("layer1", chocolateTexture);
    }

    public void registerModels() {
        for (Item toBeChocolated : Config.fisheItems) {
            this.chocolateItem(toBeChocolated);
        }
    }

}