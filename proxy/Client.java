package com.dyn.item.proxy;

import com.dyn.item.ItemMod;
import com.dyn.item.items.Flags;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Client implements Proxy {

	@SubscribeEvent
	public void dropEvent(ItemTossEvent event) {
		if ((event.player != null) && (event.entityItem != null) && (event.entityItem.getEntityItem() != null)) {
			ItemStack is = event.entityItem.getEntityItem();
			if (is.getItem() instanceof Flags) {
				event.setCanceled(true);
				for (int i = 0; i < event.player.inventory.getSizeInventory(); i++) {
					if (event.player.inventory.getStackInSlot(i) == null) {
						event.player.inventory.setInventorySlotContents(i, is);
						break;
					}
				}
			}
		}
	}

	@Override
	public void init() {

		/*
		 * Item itemBlockSimple = GameRegistry.findItem(Reference.MOD_ID,
		 * "chunk_loader"); ModelResourceLocation itemModelResourceLocation =
		 * new ModelResourceLocation("dynitems:chunk_loader", "inventory");
		 * Minecraft.getMinecraft().getRenderItem().getItemModelMesher().
		 * register(itemBlockSimple, 0, itemModelResourceLocation);
		 */

		/*Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(Item.getItemFromBlock(ItemMod.chunkloader), 0, new ModelResourceLocation(
						 Reference.MOD_ID + "dyn:" + ItemMod.chunkloader.getUnlocalizedName(), "inventory"));*/
		FMLCommonHandler.instance().bus().register(this);

		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}

}