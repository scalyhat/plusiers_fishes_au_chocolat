package com.scalyhat.pfac.providers;

import com.scalyhat.pfac.Config;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public class DynamicLanguageProvider extends LanguageProvider {
    public DynamicLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        for (Item toBeTranslated : Config.fisheItems) {
            this.add("item.pfac.".concat(toBeTranslated.toString()).concat("_au_chocolat"), "Some Item");
        }
    }
}