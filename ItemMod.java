package com.dyn.item;

import com.dyn.item.blocks.DynBlockManager;
import com.dyn.item.blocks.cmdblock.StudentCommandBlock;
import com.dyn.item.blocks.cmdblock.tileentity.TileEntityStudentCommandBlock;
import com.dyn.item.items.DynItemManager;
import com.dyn.item.proxy.Proxy;
import com.dyn.item.reference.MetaData;
import com.dyn.item.reference.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:dyn|server")
public class ItemMod {
	@Mod.Instance(Reference.MOD_ID)
	public static ItemMod instance;

	@SidedProxy(modId = Reference.MOD_ID, clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static Proxy proxy;

	public static StudentCommandBlock studentCmdBlock;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		proxy.registerBlockItem(studentCmdBlock);
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MetaData.init(event.getModMetadata());
		DynItemManager.load();
		DynBlockManager.load();

		// each instance of your block should have a name that is unique within
		// your mod. use lower case.
		studentCmdBlock = (StudentCommandBlock) (new StudentCommandBlock().setBlockUnbreakable()
				.setResistance(6000000.0F).setUnlocalizedName("student_command_block"));
		// GameRegistry.registerBlock(blockTileEntityData,
		// "student_command_block");
		GameRegistry.registerBlock(studentCmdBlock, "student_command_block");
		// you don't need to register an item corresponding to the block,
		// GameRegistry.registerBlock does this automatically.
		GameRegistry.registerTileEntity(TileEntityStudentCommandBlock.class, "student_command_block_te");

		/*
		 * printerBlock = new
		 * BlockPrinter().setHardness(0.2F).setStepSound(Block.soundTypePiston).
		 * setUnlocalizedName("PrinterBlock");
		 * GameRegistry.registerBlock(printerBlock, "PrinterBlock");
		 *
		 *
		 * channel =
		 * NetworkRegistry.INSTANCE.newEventDrivenChannel("PrinterBlock");
		 * channel.register(new PacketHandler());
		 */

		// blockSimple = (BlockSimple) (new
		// BlockSimple().setUnlocalizedName("block_test"));
		// GameRegistry.registerBlock(blockSimple, "block_test");
	}
}