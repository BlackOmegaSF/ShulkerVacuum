package com.kleinercode.fabric.shulkervacuum;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShulkerVacuum implements DedicatedServerModInitializer {

    public static final String MOD_ID = "shulkervacuum";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeServer() {

        // Add listener and processing for pickup item event
        PlayerPickUpItemCallback.EVENT.register((inventory, stack) -> {
            // Ignore all this logic for picking up shulker boxes
            if (Utils.checkIfShulkerBox(stack)) {
                return InteractionResult.PASS;
            }

            ItemStack offhand = inventory.player.getOffhandItem();
            if (!Utils.checkIfShulkerBox(offhand)) {
                return InteractionResult.PASS;
            }
            // Player has shulker box in offhand, try to put the item in there
            ItemContainerContents container = offhand.get(DataComponents.CONTAINER);
            NonNullList<ItemStack> slots = NonNullList.withSize(27, ItemStack.EMPTY);
            if (container == null) {
                // Unable to fetch shulker inventory, just pass event
                return InteractionResult.PASS;
            }
            container.copyInto(slots);
            boolean updatedItemFlag = false;
            do {
                int availableSlot = Utils.getSlotWithRoomForStack(stack, slots);
                if (availableSlot == -1) {
                    // There's no room in the box
                    if (updatedItemFlag) {
                        offhand.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(slots));
                        return InteractionResult.SUCCESS;
                    } else {
                        return InteractionResult.PASS;
                    }
                }
                stack.setCount(Utils.addStack(availableSlot, stack, slots));
                if (stack.isEmpty()) {
                    offhand.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(slots));
                    return InteractionResult.CONSUME;
                }
                updatedItemFlag = true;
            } while(true);

        });

    }
}
