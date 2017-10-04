package com.dyn.fixins.proxy;

import com.dyn.fixins.blocks.cmdblock.StudentCommandBlockLogic;
import com.dyn.fixins.blocks.decision.DecisionBlockTileEntity;
import com.dyn.fixins.blocks.dialog.DialogBlockTileEntity;
import com.dyn.fixins.blocks.redstone.proximity.ProximityBlockTileEntity;
import com.dyn.fixins.blocks.redstone.timer.TimerBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Server implements Proxy {

	@Override
	public void init() {

	}
	
	@Override
	public void addScheduledTask(Runnable runnable) {
		FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(runnable);
	}

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}
	
	/**
	 * Returns the current thread based on side during message handling, used
	 * for ensuring that the message is being handled by the main thread
	 */
	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getServerForPlayer();
	}

	@Override
	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preInit() {

	}

	@Override
	public void registerBlockItem(Block block) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerItem(Item item, String name) {
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name);
	}

	@Override
	public void registerItemModels(Item item, String name, int meta) {
	}

	@Override
	public void toggleDialogHud(EntityLivingBase entity, boolean state, String text, int duration, boolean interupt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openSetTimerInterface(TimerBlockTileEntity block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openSetProximityInterface(ProximityBlockTileEntity block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openEditDialogInterface(DialogBlockTileEntity block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openEditDecisionBlock(DecisionBlockTileEntity decisionBlock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openDecisionGui(EntityLivingBase entity, DecisionBlockTileEntity decisionBlockTileEntity) {
		// TODO Auto-generated method stub
		
	}

}