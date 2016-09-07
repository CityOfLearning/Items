package com.dyn.item.proxy;

import com.dyn.item.blocks.cmdblock.StudentCommandBlockLogic;
import com.dyn.item.gui.command.StudentComamndGui;
import com.dyn.item.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
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
		Item studentCmdBlock = GameRegistry.findItem("dynitems", "student_command_block");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(studentCmdBlock, 0,
				new ModelResourceLocation(new ResourceLocation("dynitems", "textures/blocks/student_command_block.png"),
						null));

	}

	@Override
	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic) {
		Minecraft.getMinecraft().displayGuiScreen(new StudentComamndGui(cmdBlockLogic));
	}

	@Override
	public void registerItem(Item item, String name, int meta) {
		ModelResourceLocation location = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, location);
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}

}