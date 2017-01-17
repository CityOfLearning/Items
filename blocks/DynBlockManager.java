package com.dyn.fixins.blocks;

import com.dyn.DYNServerMod;
import com.dyn.fixins.DynFixinsMod;
import com.dyn.fixins.blocks.cmdblock.StudentCommandBlock;
import com.dyn.fixins.blocks.cmdblock.tileentity.StudentCommandBlockTileEntity;
import com.dyn.fixins.blocks.dialog.DialogBlock;
import com.dyn.fixins.blocks.dialog.DialogBlockTileEntity;
import com.dyn.fixins.blocks.dialog.StudentDialogBlock;
import com.dyn.fixins.blocks.redstone.proximity.ProximityBlock;
import com.dyn.fixins.blocks.redstone.proximity.ProximityBlockTileEntity;
import com.dyn.fixins.blocks.redstone.proximity.StudentProximityBlock;
import com.dyn.fixins.blocks.redstone.timer.StudentTimerBlock;
import com.dyn.fixins.blocks.redstone.timer.TimerBlock;
import com.dyn.fixins.blocks.redstone.timer.TimerBlockTileEntity;
import com.dyn.fixins.tab.DYNTab;
import com.dyn.utils.PlayerAccessLevel;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import noppes.npcs.CustomItems;

public class DynBlockManager {

	public static Block studentDialogBlock;
	public static Block studentCmdBlock;
	public static Block studentProximityBlock;
	public static Block studentTimerBlock;

	public static Block dialogBlock;
	public static Block proximityBlock;
	public static Block timerBlock;

	public static CreativeTabs dynTab = new DYNTab();

	public static void load() {
		studentCmdBlock = (new StudentCommandBlock().setBlockUnbreakable().setResistance(6000000.0F)
				.setUnlocalizedName("student_command_block")).setCreativeTab(dynTab);
		GameRegistry.registerBlock(studentCmdBlock, "student_command_block");
		GameRegistry.registerTileEntity(StudentCommandBlockTileEntity.class, "student_command_block_te");

		dialogBlock = (new DialogBlock().setUnlocalizedName("dialog_block"));
		GameRegistry.registerBlock(dialogBlock, "dialog_block");
		GameRegistry.registerTileEntity(DialogBlockTileEntity.class, "dialog_block_te");

		studentDialogBlock = (new StudentDialogBlock().setUnlocalizedName("student_dialog_block")
				.setCreativeTab(dynTab));
		GameRegistry.registerBlock(studentDialogBlock, "student_dialog_block");

		proximityBlock = new ProximityBlock().setUnlocalizedName("proximity_block");
		GameRegistry.registerBlock(proximityBlock, "proximity_block");
		GameRegistry.registerTileEntity(ProximityBlockTileEntity.class, "proximity_block_te");

		timerBlock = new TimerBlock().setUnlocalizedName("timer_block");
		GameRegistry.registerBlock(timerBlock, "timer_block");
		GameRegistry.registerTileEntity(TimerBlockTileEntity.class, "timer_block_te");

		studentProximityBlock = new StudentProximityBlock().setUnlocalizedName("student_proximity_block")
				.setCreativeTab(dynTab);
		GameRegistry.registerBlock(studentProximityBlock, "student_proximity_block");

		studentTimerBlock = new StudentTimerBlock().setUnlocalizedName("student_timer_block").setCreativeTab(dynTab);
		GameRegistry.registerBlock(studentTimerBlock, "student_timer_block");

		if (DYNServerMod.developmentEnvironment || (DYNServerMod.accessLevel != PlayerAccessLevel.STUDENT)) {
			dialogBlock.setCreativeTab(dynTab);
			proximityBlock.setCreativeTab(dynTab);
			timerBlock.setCreativeTab(dynTab);

		}

		if ((FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
				&& (DYNServerMod.accessLevel != PlayerAccessLevel.ADMIN)) {
			DYNServerMod.logger.info("Unregistering Items");
			CustomItems.wand.setCreativeTab(null);
			CustomItems.cloner.setCreativeTab(null);
			CustomItems.scripter.setCreativeTab(null);
			CustomItems.moving.setCreativeTab(null);
			CustomItems.mount.setCreativeTab(null);
			CustomItems.teleporter.setCreativeTab(null);
			CustomItems.redstoneBlock.setCreativeTab(null);
			CustomItems.waypoint.setCreativeTab(null);
			CustomItems.border.setCreativeTab(null);
			CustomItems.scripted.setCreativeTab(null);
			CustomItems.scriptedDoor.setCreativeTab(null);
			CustomItems.scriptedDoorTool.setCreativeTab(null);
			CustomItems.builder.setCreativeTab(null);
			CustomItems.copy.setCreativeTab(null);
			CustomItems.trading.setCreativeTab(null);
		}
	}

	public static void register() {
		// Utility Items
		DynFixinsMod.proxy.registerBlockItem(dialogBlock);
		DynFixinsMod.proxy.registerBlockItem(proximityBlock);
		DynFixinsMod.proxy.registerBlockItem(timerBlock);

		// Student Items
		DynFixinsMod.proxy.registerBlockItem(studentCmdBlock);
		DynFixinsMod.proxy.registerBlockItem(studentDialogBlock);
		DynFixinsMod.proxy.registerBlockItem(studentProximityBlock);
		DynFixinsMod.proxy.registerBlockItem(studentTimerBlock);
	}
}