package com.kleinercode.fabric.shulkervacuum.mixin;

import com.kleinercode.fabric.shulkervacuum.PlayerPickUpItemCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class PlayerInventoryMixin {

    @Inject(method = "add(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void onInsertStack(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        final Inventory inventory = (Inventory)(Object)this;
        InteractionResult result = PlayerPickUpItemCallback.EVENT.invoker().interact(inventory, stack);

        if (result.equals(InteractionResult.CONSUME)) {
            info.setReturnValue(true);
        } else if (result.equals(InteractionResult.FAIL)) {
            info.setReturnValue(false);
        }

    }


}
