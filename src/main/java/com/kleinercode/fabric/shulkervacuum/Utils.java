package com.kleinercode.fabric.shulkervacuum;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

public class Utils {

    public static int getSlotWithRoomForStack(ItemStack stack, List<ItemStack> slots) {
        if (stack.isEmpty()) return -1;
        for (int i = 0; i < slots.size(); i++) {
            ItemStack slot = slots.get(i);
            if (slot.isEmpty()) return i;
            if (ItemStack.areItemsAndComponentsEqual(stack, slot) && slot.isStackable() && slot.getCount() < slot.getMaxCount()) {
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

        int j = slots.get(slot).getMaxCount() - itemStack.getCount();
        int k = Math.min(i, j);
        if (k != 0) {
            i -= k;
            itemStack.increment(k);
            itemStack.setBobbingAnimationTime(5);
        }
        slots.set(slot, itemStack);
        return i;
    }

    public static boolean checkIfShulkerBox(ItemStack stack) {
        if (stack.isOf(Items.SHULKER_BOX)) return true;
        if (stack.isOf(Items.BLACK_SHULKER_BOX)) return true;
        if (stack.isOf(Items.BLUE_SHULKER_BOX)) return true;
        if (stack.isOf(Items.BROWN_SHULKER_BOX)) return true;
        if (stack.isOf(Items.CYAN_SHULKER_BOX)) return true;
        if (stack.isOf(Items.GRAY_SHULKER_BOX)) return true;
        if (stack.isOf(Items.GREEN_SHULKER_BOX)) return true;
        if (stack.isOf(Items.LIGHT_BLUE_SHULKER_BOX)) return true;
        if (stack.isOf(Items.LIGHT_GRAY_SHULKER_BOX)) return true;
        if (stack.isOf(Items.LIME_SHULKER_BOX)) return true;
        if (stack.isOf(Items.MAGENTA_SHULKER_BOX)) return true;
        if (stack.isOf(Items.ORANGE_SHULKER_BOX)) return true;
        if (stack.isOf(Items.PINK_SHULKER_BOX)) return true;
        if (stack.isOf(Items.PURPLE_SHULKER_BOX)) return true;
        if (stack.isOf(Items.RED_SHULKER_BOX)) return true;
        if (stack.isOf(Items.WHITE_SHULKER_BOX)) return true;
        if (stack.isOf(Items.YELLOW_SHULKER_BOX)) return true;
        return false;
    }

}
