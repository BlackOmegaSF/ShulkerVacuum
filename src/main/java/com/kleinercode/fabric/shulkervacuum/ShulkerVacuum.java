package com.kleinercode.fabric.shulkervacuum;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ShulkerVacuum implements DedicatedServerModInitializer {

    public static final String MOD_ID = "shulkervacuum";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeServer() {

        // Add listener and processing for pickup item event
        PlayerPickUpItemCallback.EVENT.register((inventory, stack) -> {
            // Ignore all this logic for picking up shulker boxes
            if (Utils.checkIfShulkerBox(stack)) {
                return ActionResult.PASS;
            }

            ItemStack offhand = inventory.player.getOffHandStack();
            if (!Utils.checkIfShulkerBox(offhand)) {
                return ActionResult.PASS;
            }
            // Player has shulker box in offhand, try to put the item in there
            ContainerComponent container = offhand.get(DataComponentTypes.CONTAINER);
            List<ItemStack> tempSlots = new ArrayList<>(container.stream().toList());
            DefaultedList<ItemStack> slots = DefaultedList.ofSize(27, ItemStack.EMPTY);
            for (int i = 0; i < tempSlots.size(); i++) {
                slots.set(i, tempSlots.get(i));
            }
            boolean updatedItemFlag = false;
            do {
                int availableSlot = Utils.getSlotWithRoomForStack(stack, slots);
                if (availableSlot == -1) {
                    // There's no room in the box
                    if (updatedItemFlag) {
                        offhand.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(slots));
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.PASS;
                    }
                }
                stack.setCount(Utils.addStack(availableSlot, stack, slots));
                if (stack.isEmpty()) {
                    offhand.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(slots));
                    return ActionResult.CONSUME;
                }
                updatedItemFlag = true;
            } while(true);

        });

    }
}
