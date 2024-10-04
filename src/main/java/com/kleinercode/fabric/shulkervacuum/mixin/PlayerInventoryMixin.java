package com.kleinercode.fabric.shulkervacuum.mixin;

import com.kleinercode.fabric.shulkervacuum.PlayerPickUpItemCallback;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {

    @Inject(method = "insertStack(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void onInsertStack(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        final PlayerInventory inventory = (PlayerInventory)(Object)this;
        ActionResult result = PlayerPickUpItemCallback.EVENT.invoker().interact(inventory, stack);

        switch (result) {
            case ActionResult.CONSUME -> info.setReturnValue(true);
            case ActionResult.FAIL -> info.setReturnValue(false);
        }
    }


}
