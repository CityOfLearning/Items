package com.dyn.item.proxy;

import com.dyn.item.items.Flags;
import com.dyn.server.packets.PacketDispatcher;
import com.dyn.server.packets.client.ReturnFlagMessage;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;

public class Client implements Proxy {

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}

	@Override
	public void init() {
		FMLCommonHandler.instance().bus().register(this);

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void dropEvent(ItemTossEvent event) {
		if (event.player != null && event.entityItem != null && event.entityItem.getEntityItem() != null) {
			ItemStack is = event.entityItem.getEntityItem();
			if (is.getItem() instanceof Flags) {
				event.setCanceled(true);
				for(int i=0;i<event.player.inventory.getSizeInventory();i++){
					if(event.player.inventory.getStackInSlot(i) == null){
						event.player.inventory.setInventorySlotContents(i, is);
						break;
					}
				}
			}
		}
	}
	
}