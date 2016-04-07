package com.dyn.item.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class FlagBlockVariants extends ItemBlock {
	// you must use Block in the constructor, not BlockVariants, otherwise you
	// won't be able to register the block properly.
	// i.e. using GameRegistry.registerBlock(block, ItemBlockVariants.class,
	// name)
	public FlagBlockVariants(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int metadata) {
		return metadata;
	}

	// create a unique unlocalised name for each colour, so that we can give
	// each one a unique name
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		// FlagTypes. colour =
		// BlockVariants.EnumColour.byMetadata(stack.getMetadata());
		return super.getUnlocalizedName();// + "." + colour.toString();
	}
}
