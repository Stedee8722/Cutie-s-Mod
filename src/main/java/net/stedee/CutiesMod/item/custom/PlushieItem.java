package net.stedee.CutiesMod.item.custom;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.stedee.CutiesMod.sound.ModdedSounds;
import org.jetbrains.annotations.NotNull;

public class PlushieItem extends BlockItem {
    public PlushieItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public @NotNull InteractionResult use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        if (!pLevel.isClientSide) {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModdedSounds.PLUSHIE_SQUEAKS.get(), SoundSource.PLAYERS, 1f, 1f);
            pPlayer.startUsingItem(pHand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        Player pPlayer = pContext.getPlayer();
        if (super.useOn(pContext) == InteractionResult.FAIL && pPlayer != null) {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModdedSounds.PLUSHIE_SQUEAKS.get(), SoundSource.PLAYERS, 1f, 1f);
        }
        return InteractionResult.PASS;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack, @NotNull LivingEntity pEntity) {
        return 100000;
    }
}
