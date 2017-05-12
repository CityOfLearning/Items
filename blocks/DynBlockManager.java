package com.dyn.fixins.blocks;

import com.dyn.DYNServerMod;
import com.dyn.fixins.DynFixinsMod;
import com.dyn.fixins.blocks.cmdblock.StudentCommandBlock;
import com.dyn.fixins.blocks.cmdblock.StudentCommandBlockTileEntity;
import com.dyn.fixins.blocks.decision.DecisionBlock;
import com.dyn.fixins.blocks.decision.DecisionBlockTileEntity;
import com.dyn.fixins.blocks.dialog.DialogBlock;
import com.dyn.fixins.blocks.dialog.DialogBlockTileEntity;
import com.dyn.fixins.blocks.dialog.StudentDialogBlock;
import com.dyn.fixins.blocks.redstone.delay.DelaySignalBlock;
import com.dyn.fixins.blocks.redstone.latch.RedstoneLatchBlock;
import com.dyn.fixins.blocks.redstone.light.RedstoneIndicationLight;
import com.dyn.fixins.blocks.redstone.proximity.ProximityBlock;
import com.dyn.fixins.blocks.redstone.proximity.ProximityBlockTileEntity;
import com.dyn.fixins.blocks.redstone.proximity.StudentProximityBlock;
import com.dyn.fixins.blocks.redstone.timer.StudentTimerBlock;
import com.dyn.fixins.blocks.redstone.timer.TimerBlock;
import com.dyn.fixins.blocks.redstone.timer.TimerBlockTileEntity;
import com.dyn.utils.PlayerAccessLevel;

import net.minecraft.block.Block;
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
	public static Block decisionBlock;

	public static Block delayBlock;
	public static Block delayBlockOff;

	public static Block latchBLock;

	public static Block indcationLightOn;
	public static Block indcationLightOff;

	// public static Block arrowBlock;

	public static void load() {
		studentCmdBlock = (new StudentCommandBlock().setBlockUnbreakable().setResistance(6000000.0F)
				.setUnlocalizedName("student_command_block")).setCreativeTab(DynFixinsMod.dynTab);
		GameRegistry.registerBlock(studentCmdBlock, "student_command_block");
		GameRegistry.registerTileEntity(StudentCommandBlockTileEntity.class, "student_command_block_te");

		dialogBlock = (new DialogBlock().setUnlocalizedName("dialog_block"));
		GameRegistry.registerBlock(dialogBlock, "dialog_block");
		GameRegistry.registerTileEntity(DialogBlockTileEntity.class, "dialog_block_te");

		studentDialogBlock = (new StudentDialogBlock().setUnlocalizedName("student_dialog_block")
				.setCreativeTab(DynFixinsMod.dynTab));
		GameRegistry.registerBlock(studentDialogBlock, "student_dialog_block");

		proximityBlock = new ProximityBlock().setUnlocalizedName("proximity_block");
		GameRegistry.registerBlock(proximityBlock, "proximity_block");
		GameRegistry.registerTileEntity(ProximityBlockTileEntity.class, "proximity_block_te");

		timerBlock = new TimerBlock().setUnlocalizedName("timer_block");
		GameRegistry.registerBlock(timerBlock, "timer_block");
		GameRegistry.registerTileEntity(TimerBlockTileEntity.class, "timer_block_te");

		decisionBlock = new DecisionBlock().setUnlocalizedName("decision_block");
		GameRegistry.registerBlock(decisionBlock, "decision_block");
		GameRegistry.registerTileEntity(DecisionBlockTileEntity.class, "decision_block_te");

		studentProximityBlock = new StudentProximityBlock().setUnlocalizedName("student_proximity_block")
				.setCreativeTab(DynFixinsMod.dynTab);
		GameRegistry.registerBlock(studentProximityBlock, "student_proximity_block");

		studentTimerBlock = new StudentTimerBlock().setUnlocalizedName("student_timer_block")
				.setCreativeTab(DynFixinsMod.dynTab);
		GameRegistry.registerBlock(studentTimerBlock, "student_timer_block");

		// arrowBlock = (new
		// BlockArrowSign().setUnlocalizedName("arrow_block")).setCreativeTab(dynTab);
		// GameRegistry.registerBlock(arrowBlock, "arrow_block");

		delayBlock = new DelaySignalBlock(true).setUnlocalizedName("delay_block").setHardness(0.0F)
				.setStepSound(Block.soundTypeWood);
		GameRegistry.registerBlock(delayBlock, "delay_block");

		delayBlockOff = new DelaySignalBlock(false).setUnlocalizedName("delay_block_off").setHardness(0.0F)
				.setStepSound(Block.soundTypeWood);
		GameRegistry.registerBlock(delayBlockOff, "delay_block_off");

		latchBLock = new RedstoneLatchBlock().setUnlocalizedName("rs_latch").setHardness(0.0F)
				.setStepSound(Block.soundTypeWood);
		GameRegistry.registerBlock(latchBLock, "rs_latch");

		indcationLightOn = new RedstoneIndicationLight(true).setUnlocalizedName("indication_lamp_on")
				.setCreativeTab(DynFixinsMod.dynTab);
		GameRegistry.registerBlock(indcationLightOn, "indication_lamp_on");

		indcationLightOff = new RedstoneIndicationLight(false).setUnlocalizedName("indication_lamp_off");
		GameRegistry.registerBlock(indcationLightOff, "indication_lamp_off");

		if (DYNServerMod.developmentEnvironment || (DYNServerMod.accessLevel != PlayerAccessLevel.STUDENT)) {
			dialogBlock.setCreativeTab(DynFixinsMod.dynTab);
			proximityBlock.setCreativeTab(DynFixinsMod.dynTab);
			timerBlock.setCreativeTab(DynFixinsMod.dynTab);
			decisionBlock.setCreativeTab(DynFixinsMod.dynTab);
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
		DynFixinsMod.proxy.registerBlockItem(decisionBlock);
		// DynFixinsMod.proxy.registerBlockItem(arrowBlock);
		DynFixinsMod.proxy.registerBlockItem(indcationLightOn);
		DynFixinsMod.proxy.registerBlockItem(indcationLightOff);

		// Student Items
		DynFixinsMod.proxy.registerBlockItem(studentCmdBlock);
		DynFixinsMod.proxy.registerBlockItem(studentDialogBlock);
		DynFixinsMod.proxy.registerBlockItem(studentProximityBlock);
		DynFixinsMod.proxy.registerBlockItem(studentTimerBlock);
	}
}