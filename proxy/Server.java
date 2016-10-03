package com.dyn.item.proxy;

import com.dyn.item.blocks.cmdblock.StudentCommandBlockLogic;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Server implements Proxy {

	/*
	 * @SubscribeEvent public void dropEvent(ItemTossEvent event) { if
	 * ((event.player != null) && (event.entityItem != null) &&
	 * (event.entityItem.getEntityItem() != null)) { ItemStack is =
	 * event.entityItem.getEntityItem(); if (is.getItem() instanceof Flags) {
	 * event.setCanceled(true); PacketDispatcher.sendTo(new
	 * ReturnFlagMessage(is), (EntityPlayerMP) event.player); } } }
	 */

	@Override
	public void init() {
		// MinecraftForge.EVENT_BUS.register(this);

	}

	@Override
	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerBlock(Block block, String name, int meta, Class<? extends ItemBlock> itemclass) {
		this.registerBlock(block, name, meta, itemclass, false);
	}

	@Override
	public void registerBlock(Block block, String name, int meta, Class<? extends ItemBlock> itemclass,
			boolean seperateMetadata) {
		GameRegistry.registerBlock(block, itemclass, name);
	}

	@Override
	public void registerBlockItem(Block block) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerItem(Item item, String name, int meta) {
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Actions on render GUI for the server (logging)

	}

	@Override
	public void preInit() {
		// TODO Auto-generated method stub
		
	}

}