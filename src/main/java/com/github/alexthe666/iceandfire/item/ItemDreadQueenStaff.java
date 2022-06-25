package com.github.alexthe666.iceandfire.item;

import net.minecraft.item.Item;

public class ItemDreadQueenStaff extends ItemGeneric {

    public ItemDreadQueenStaff() {
        super("dread_queen_staff", "iceandfire.dread_queen_staff");
        this.maxStackSize = 1;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        if(!worldIn.isRemote){
            playerIn.setActiveHand(hand);
            playerIn.swingArm(hand);
            double d2 = playerIn.getLookVec().x;
            double d3 = playerIn.getLookVec().y;
            double d4 = playerIn.getLookVec().z;
            float inaccuracy = 1.0F;
            d2 = d2 + playerIn.getRNG().nextGaussian() * 0.007499999832361937D * inaccuracy;
            d3 = d3 + playerIn.getRNG().nextGaussian() * 0.007499999832361937D * inaccuracy;
            d4 = d4 + playerIn.getRNG().nextGaussian() * 0.007499999832361937D * inaccuracy;
            EntityDreadLichSkull charge = new EntityDreadLichSkull(IafEntityRegistry.DREAD_LICH_SKULL.get(), worldIn,
                playerIn, 6);
            charge.shoot( playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 7.0F, 1.0F);
            charge.setPosition(playerIn.getPosX(), playerIn.getPosY() + 1, playerIn.getPosZ());
            worldIn.addEntity(charge);
            charge.shoot(d2, d3, d4, 1, 1);
            playerIn.playSound(SoundEvents.ENTITY_ZOMBIE_INFECT, 1F, 0.75F + 0.5F * playerIn.getRNG().nextFloat());
            itemStackIn.damageItem(1, playerIn, (player) -> {
                player.sendBreakAnimation(hand);
            });
            playerIn.getCooldownTracker().setCooldown(this, 4);
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, itemStackIn);
    }
}
