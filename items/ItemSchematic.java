package com.dyn.fixins.items;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dyn.schematics.Schematic;
import com.dyn.schematics.SchematicRegistry;
import com.dyn.schematics.SchematicRenderingRegistry;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.controllers.SchematicController;

public class ItemSchematic extends Item {
	private static final Map<Integer, String> SCHEMS = Maps.<Integer, String>newHashMap();

	private static void createMappings() {
		int counter = 0;
		for (String schem : SchematicController.instance.list()) {
			SCHEMS.put(counter++, schem);
		}
		for (String schem : SchematicRegistry.enumerateSchematics()) {
			SCHEMS.put(counter++, schem);
		}
	}

	public static Schematic getSchematic(ItemStack itemStackIn) {
		if (SCHEMS.size() == 0) {
			createMappings();
		}
		if (SchematicController.instance.included.contains(SCHEMS.get(itemStackIn.getItemDamage()))) {
			return SchematicController.instance.load(SCHEMS.get(itemStackIn.getItemDamage()));
		} else {
			return SchematicRegistry.load(SCHEMS.get(itemStackIn.getItemDamage()));
		}
	}

	public ItemSchematic() {
		maxStackSize = 1;
		setHasSubtypes(true);
	}

	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(EnumChatFormatting.DARK_AQUA + SCHEMS.get(stack.getItemDamage()));
		tooltip.add("");
		int counter = 0;
		if (SchematicController.instance.included.contains(SCHEMS.get(stack.getItemDamage()))) {
			for (Entry<Block, Integer> block : SchematicController.instance.load(SCHEMS.get(stack.getItemDamage()))
					.getMaterialCosts().entrySet()) {
				if (counter > 5) {
					tooltip.add("Etc...");
					break;
				}
				tooltip.add(EnumChatFormatting.GOLD + block.getKey().getLocalizedName() + EnumChatFormatting.RESET
						+ ": " + EnumChatFormatting.GRAY + block.getValue());
				counter++;
			}
		} else {
			for (Entry<Block, Integer> block : SchematicRegistry.load(SCHEMS.get(stack.getItemDamage()))
					.getMaterialCosts().entrySet()) {
				if (counter > 5) {
					tooltip.add("Etc...");
					break;
				}
				tooltip.add(EnumChatFormatting.GOLD + block.getKey().getLocalizedName() + EnumChatFormatting.RESET
						+ ": " + EnumChatFormatting.GRAY + block.getValue());
				counter++;
			}
		}
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		int counter = 0;
		for (String schem : SchematicController.instance.list()) {
			subItems.add(new ItemStack(itemIn, 1, counter));
			SCHEMS.put(counter++, schem);
		}
		for (String schem : SchematicRegistry.enumerateSchematics()) {
			subItems.add(new ItemStack(itemIn, 1, counter));
			SCHEMS.put(counter++, schem);
		}
		// File otherDir = new File(CustomNpcs.Dir.getParent(),
		// "config/worldedit/schematics");
		// if (otherDir.exists()) {
		// for (File schem : otherDir.listFiles(new FilenameFilter() {
		// @Override
		// public boolean accept(File dir, String name) {
		// return name.endsWith("schematic");
		// }
		// })) {
		// subItems.add(new ItemStack(itemIn, 1, counter));
		// SCHEMS.put(counter++, schem.getName().replace(".schematic", ""));
		// }
		// }
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if (worldIn.isRemote) {
			if (SchematicController.instance.included.contains(SCHEMS.get(itemStackIn.getItemDamage()))) {
				SchematicRenderingRegistry
						.removeSchematic(SchematicController.instance.load(SCHEMS.get(itemStackIn.getItemDamage())));
			} else {
				SchematicRenderingRegistry
						.removeSchematic(SchematicRegistry.load(SCHEMS.get(itemStackIn.getItemDamage())));
			}
		}
		return itemStackIn;
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {

		if (worldIn.isRemote) {
			if (SchematicController.instance.included.contains(SCHEMS.get(stack.getItemDamage()))) {
				Schematic schem = SchematicController.instance.load(SCHEMS.get(stack.getItemDamage()));
				if (schem != null) {
					if (SchematicRenderingRegistry.containsCompiledSchematic(schem, pos)) {
						SchematicRenderingRegistry.rotateSchematic(schem);
					} else {
						SchematicRenderingRegistry.addSchematic(schem, pos, 0);
					}
				}

				return true;
			} else {
				Schematic schem = SchematicRegistry.load(SCHEMS.get(stack.getItemDamage()));
				if (schem != null) {
					if (SchematicRenderingRegistry.containsCompiledSchematic(schem, pos)) {
						SchematicRenderingRegistry.rotateSchematic(schem);
					} else {
						SchematicRenderingRegistry.addSchematic(schem, pos, 0);
					}
				}

				return true;
			}
		}
		return true;
	}
}
