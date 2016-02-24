package com.dyn.item.proxy;

import com.dyn.item.items.Flags;
import com.dyn.item.reference.Reference;
import com.dyn.server.packets.PacketDispatcher;
import com.dyn.server.packets.client.ReturnFlagMessage;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
		
		/*Item itemBlockSimple = GameRegistry.findItem(Reference.MOD_ID, "chunk_loader");
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(new ResourceLocation("dyn", "textures/blocks/chunkloader.png"), "normal");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlockSimple, 0,
				itemModelResourceLocation);*/
		
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