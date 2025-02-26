package net.stedee.CutiesMod.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import net.stedee.CutiesMod.CutiesMod;
import net.stedee.CutiesMod.sound.ModdedSounds;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PlushAymBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<PlushAymBlock> CODEC = simpleCodec(PlushAymBlock::new);
    private static final VoxelShape PLUSH_AYM_SHAPE = makeShape();

    public PlushAymBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @NotNull Item.TooltipContext pContext, List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("tooltip." + CutiesMod.MODID + "." + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(pStack.getItem())).getPath()));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull BlockHitResult pHitResult) {
        if (!pPlayer.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), ModdedSounds.PLUSHIE_SQUEAKS.get(), SoundSource.BLOCKS, 1f, 1f);
            return InteractionResult.SUCCESS;
        }
    }

    @SuppressWarnings("null")
    @Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState pState) {
        return true;
    }

    @SuppressWarnings("null")
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return PLUSH_AYM_SHAPE;
    }

    @SuppressWarnings("null")
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.1875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.0625, 0.1875, 0.8125, 1.1875, 0.8125), BooleanOp.OR);

        return shape;
    }

    @SuppressWarnings("null")
    @Override
    public boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        return canSupportCenter(pLevel, pPos.below(), Direction.UP);
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull ScheduledTickAccess pScheduledTickAccess, @NotNull BlockPos pPos, @NotNull Direction pDirection, @NotNull BlockPos pNeighborPos, @NotNull BlockState pNeighborState, @NotNull RandomSource pRandom) {
        if ((pDirection == Direction.DOWN) && !this.canSurvive(pState, pLevel, pPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(pState, pLevel, pScheduledTickAccess, pPos, pDirection, pNeighborPos, pNeighborState, pRandom);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    protected float getDestroyProgress(@NotNull BlockState pState, @NotNull Player pPlayer, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
        float f = pState.getDestroySpeed(pLevel, pPos);
        if (f == -1.0F) {
            return 0.0F;
        } else {
            int i = net.minecraftforge.common.ForgeHooks.isCorrectToolForDrops(pState, pPlayer) ? 30 : 100;
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(Items.SHEARS)) {
                i = i - 29;
            }
            return pPlayer.getDestroySpeed(pState, pPos) / f / (float)i;
        }
    }
}
