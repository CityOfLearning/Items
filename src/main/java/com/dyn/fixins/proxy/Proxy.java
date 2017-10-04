package com.dyn.fixins.proxy;

import java.util.List;

import com.dyn.fixins.blocks.cmdblock.StudentCommandBlockLogic;
import com.dyn.fixins.blocks.decision.DecisionBlockTileEntity;
import com.dyn.fixins.blocks.dialog.DialogBlockTileEntity;
import com.dyn.fixins.blocks.redstone.proximity.ProximityBlockTileEntity;
import com.dyn.fixins.blocks.redstone.timer.TimerBlockTileEntity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface Proxy {
	public void init();

	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic);

	public void preInit();

	public void registerBlockItem(Block block);

	public void registerItem(Item item, String name);

	public void registerItemModels(Item item, String name, int meta);

	public void addScheduledTask(Runnable runnable);

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx);

	public IThreadListener getThreadFromContext(MessageContext ctx);
	
	void toggleDialogHud(EntityLivingBase entity, boolean state, String text, int duration, boolean interupt);

	void openSetTimerInterface(TimerBlockTileEntity block);

	void openSetProximityInterface(ProximityBlockTileEntity block);

	void openEditDialogInterface(DialogBlockTileEntity block);

	void openEditDecisionBlock(DecisionBlockTileEntity decisionBlock);

	void openDecisionGui(EntityLivingBase entity, DecisionBlockTileEntity decisionBlockTileEntity);
}