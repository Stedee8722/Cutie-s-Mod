package net.stedee.CutiesMod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.CutiesMod.CutiesMod;
import net.stedee.CutiesMod.item.custom.MoonStaffItem;

public class ModdedItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CutiesMod.MODID);

    public static final RegistryObject<MoonStaffItem> MOON_STAFF = ITEMS.register("moon_staff",
            () -> new MoonStaffItem(ToolMaterial.IRON, 10, -2.4F, (
                    new Item.Properties())
                    .durability(753)
                    .rarity(Rarity.EPIC)
                    .repairable(Items.IRON_INGOT)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CutiesMod.MODID, "moon_staff")))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
