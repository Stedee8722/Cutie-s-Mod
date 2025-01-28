package net.stedee.CutiesMod.item.custom;

import java.util.List;
import java.util.Objects;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.stedee.CutiesMod.CutiesMod;
import org.jetbrains.annotations.NotNull;

public class MoonStaffItem extends SwordItem {

    public MoonStaffItem(ToolMaterial pMaterial, float pAttackDamage, float pAttackSpeed, Properties pProperties) {
        super(pMaterial, pAttackDamage, pAttackSpeed, pProperties);
    }
    
    @Override
    public void appendHoverText(ItemStack pStack, @NotNull TooltipContext pContext, List<Component> pTooltipComponents,
                                @NotNull TooltipFlag pTooltipFlag) {
        pTooltipComponents.add(Component.translatable("tooltip." + CutiesMod.MODID + "." + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(pStack.getItem())).getPath()));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

    @Override
    public @NotNull InteractionResult use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack pItem = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1F, 1F);
        pPlayer.getCooldowns().addCooldown(pItem, 10);
        if (!pLevel.isClientSide) {
            Vec3 vec3 = pPlayer.getLookAngle();
            Vec3 look = pPlayer.getViewVector(1F);

            double d1 = vec3.x * 0.1D;
            double d2 = vec3.y * 0.1D;
            double d3 = vec3.z * 0.1D;
            Vec3 vec31 = new Vec3(d1, d2, d3);

            LargeFireball fireball = new LargeFireball(pLevel, pPlayer, vec31, 3);
            fireball.setPos(pPlayer.getX() + look.x * 0.5, pPlayer.getY() + 1.25, pPlayer.getZ() + look.z * 0.5);
            fireball.setDeltaMovement(vec3);
            pLevel.addFreshEntity(fireball);
            pItem.hurtAndBreak(3, pPlayer, EquipmentSlot.MAINHAND);
        }
        return InteractionResult.SUCCESS;
    }
}
