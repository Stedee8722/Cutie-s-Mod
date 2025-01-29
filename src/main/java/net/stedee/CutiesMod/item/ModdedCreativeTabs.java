package net.stedee.CutiesMod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.CutiesMod.CutiesMod;
import net.stedee.CutiesMod.block.ModdedBlocks;

public class ModdedCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CutiesMod.MODID);

    public static final RegistryObject<CreativeModeTab> WEAPONS = CREATIVE_MODE_TABS.register("weapons", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab.cuties_mod.weapons"))
            .icon(() -> ModdedItems.MOON_STAFF.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModdedItems.MOON_STAFF.get());
            }).build());

    public static final RegistryObject<CreativeModeTab> PLUSHIES = CREATIVE_MODE_TABS.register("plushies", () -> CreativeModeTab.builder()
            .title(Component.translatable("creativetab.cuties_mod.plushies"))
            .icon(() -> ModdedBlocks.PLUSH_AYM.get().asItem().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModdedBlocks.PLUSH_AYM.get().asItem());
            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
