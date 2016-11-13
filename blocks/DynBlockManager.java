package com.dyn.fixins.blocks;

import com.dyn.fixins.DynFixinsMod;
import com.dyn.fixins.blocks.cmdblock.StudentCommandBlock;
import com.dyn.fixins.blocks.cmdblock.tileentity.StudentCommandBlockTileEntity;
import com.dyn.fixins.blocks.dialog.DialogBlock;
import com.dyn.fixins.blocks.dialog.DialogBlockTileEntity;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DynBlockManager {

	public static Block studentCmdBlock;
	public static Block dialogBlock;
	public static Block blockSimple;

	public static void load() {
		studentCmdBlock = (new StudentCommandBlock().setBlockUnbreakable().setResistance(6000000.0F)
				.setUnlocalizedName("student_command_block"));
		GameRegistry.registerBlock(studentCmdBlock, "student_command_block");
		GameRegistry.registerTileEntity(StudentCommandBlockTileEntity.class, "student_command_block_te");

		dialogBlock = (new DialogBlock().setUnlocalizedName("dialog_block").setCreativeTab(CreativeTabs.tabMisc));
		GameRegistry.registerBlock(dialogBlock, "dialog_block");
		GameRegistry.registerTileEntity(DialogBlockTileEntity.class, "dialog_block_te");

		// blockSimple = (BlockSimple) (new
		// BlockSimple().setUnlocalizedName("block_test"));
		// GameRegistry.registerBlock(blockSimple, "block_test");
	}

	public static void register() {
		DynFixinsMod.proxy.registerBlockItem(studentCmdBlock);
		DynFixinsMod.proxy.registerBlockItem(dialogBlock);
	}
}