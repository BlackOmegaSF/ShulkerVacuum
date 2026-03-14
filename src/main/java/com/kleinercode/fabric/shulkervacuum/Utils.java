package com.kleinercode.fabric.shulkervacuum;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.world.item.ItemStack;
import java.util.List;

public class Utils {

    public static int getSlotWithRoomForStack(ItemStack stack, List<ItemStack> slots) {
        if (stack.isEmpty()) return -1;
        for (int i = 0; i < slots.size(); i++) {
            ItemStack slot = slots.get(i);
            if (slot.isEmpty()) return i;
            if (ItemStack.isSameItemSameComponents(stack, slot) && slot.isStackable() && slot.getCount() < slot.getMaxStackSize()) {
                return i;
            }
        }
        return -1;
    }

    public static int addStack(int slot, ItemStack stack, List<ItemStack> slots) {
        int i = stack.getCount();
        ItemStack itemStack = slots.get(slot);
        if (itemStack.isEmpty()) {
            itemStack = stack.copyWithCount(0);
            slots.set(slot, itemStack);
        }

        int j = slots.get(slot).getMaxStackSize() - itemStack.getCount();
        int k = Math.min(i, j);
        if (k != 0) {
            i -= k;
            itemStack.grow(k);
            itemStack.setPopTime(5);
        }
        slots.set(slot, itemStack);
        return i;
    }

    public static boolean checkIfShulkerBox(ItemStack stack) {
        return stack.is(ConventionalItemTags.SHULKER_BOXES);
    }

}
