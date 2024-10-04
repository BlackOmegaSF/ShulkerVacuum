package com.kleinercode.fabric.shulkervacuum;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface PlayerPickUpItemCallback {

    /**
     * Callback for when an item is being placed into a player's inventory, because they picked it up
     * Called before any inventory logic is checked.
     * Upon return:
     *  - SUCCESS cancels further processing and continues with adding to the player's inventory
     *  - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available
     *  - FAIL cancels further processing and returns false, as if the inventory is full
     *  - CONSUME cancels further processing and returns true. Use when your listener handles adding the item to inventory
     */

    Event<PlayerPickUpItemCallback> EVENT = EventFactory.createArrayBacked(PlayerPickUpItemCallback.class,
            (listeners) -> (inventory, stack) -> {
                for (PlayerPickUpItemCallback listener : listeners) {
                    ActionResult result = listener.interact(inventory, stack);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

            return ActionResult.PASS;
            });

    ActionResult interact(PlayerInventory inventory, ItemStack stack);

}
