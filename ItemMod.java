package com.dyn.item;

import org.apache.logging.log4j.Logger;

import com.dyn.item.proxy.Proxy;
import com.dyn.item.reference.MetaData;
import com.dyn.item.reference.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ItemMod {
	@Mod.Instance(Reference.MOD_ID)
	public static ItemMod instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static Proxy proxy;

	public static Logger logger;

	/*
	 * public static Item flags; public static Block flagBlock; public static
	 * Block chunkloader;
	 */

	@Mod.Metadata(Reference.MOD_ID)
	public ModMetadata metadata;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		metadata = MetaData.init(metadata);

		logger = event.getModLog();

		Configuration configs = new Configuration(event.getSuggestedConfigurationFile());
		try {
			configs.load();
		} catch (RuntimeException e) {
			logger.warn(e);
		}

		/*
		 * flags = new Flags(); GameRegistry.registerItem(flags, "ctfFlags");
		 *
		 * flagBlock = new FlagBlock();
		 *
		 * GameRegistry.registerBlock(flagBlock, "ctfFlagBlocks");
		 */

	}
}