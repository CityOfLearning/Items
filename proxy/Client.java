package com.dyn.item.proxy;

import com.dyn.item.blocks.cmdblock.StudentCommandBlockLogic;
import com.dyn.item.gui.command.StudentComamndGui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Client implements Proxy {

	@Override
	public void init() {
		// MinecraftForge.EVENT_BUS.register(this);

		// This is currently necessary in order to make your block render
		// properly when it is an item (i.e. in the inventory
		// or in your hand or thrown on the ground).
		// Minecraft knows to look for the item model based on the
		// GameRegistry.registerBlock. However the registration of
		// the model for each item is normally done by
		// RenderItem.registerItems(), and this is not currently aware
		// of any extra items you have created. Hence you have to do it
		// manually. This will probably change in future.
		// It must be done in the init phase, not preinit, and must be done on
		// client only.
		Item itemBlockSimple = GameRegistry.findItem("dynitems", "student_command_block");
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("dynitems:student_command_block",
				"inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlockSimple, DEFAULT_ITEM_SUBTYPE,
				itemModelResourceLocation);
	}

	@Override
	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic) {
		Minecraft.getMinecraft().displayGuiScreen(new StudentComamndGui(cmdBlockLogic));
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}

}