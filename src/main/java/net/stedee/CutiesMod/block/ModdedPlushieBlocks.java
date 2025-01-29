package net.stedee.CutiesMod.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.CutiesMod.CutiesMod;
import net.stedee.CutiesMod.block.custom.PlushAymBlock;
import net.stedee.CutiesMod.item.ModdedItems;
import net.stedee.CutiesMod.item.custom.PlushieItem;

import java.util.function.Supplier;

public class ModdedPlushieBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CutiesMod.MODID);

    public static final RegistryObject<Block> PLUSH_AYM = registerBlock("plush_aym",
            () -> new PlushAymBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NONE)
                    .noOcclusion()
                    .strength(0.3F, 1200.0F)
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(CutiesMod.MODID, "plush_aym")))));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModdedItems.ITEMS.register(name, () -> new PlushieItem(block.get(), new Item.Properties()
                .stacksTo(1)
                .equippable(EquipmentSlot.HEAD)
                .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CutiesMod.MODID, name)))));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
