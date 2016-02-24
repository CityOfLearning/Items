package com.dyn.chunkloader;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

import com.dyn.chunkloader.block.BlockChunkLoader;
import com.dyn.chunkloader.handler.ChunkLoadingHandler;
import com.dyn.chunkloader.proxy.Proxy;
import com.dyn.chunkloader.reference.Reference;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ChunkLoaderMod {
	@Mod.Instance(Reference.MOD_ID)
	public static ChunkLoaderMod instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static Proxy proxy;

	public static Block chunkloader;

	public static Logger logger;

	@Mod.EventHandler
	public void PreInit(FMLPreInitializationEvent event) {

		logger = event.getModLog();

		Configuration configs = new Configuration(event.getSuggestedConfigurationFile());
		try {
			configs.load();
		} catch (RuntimeException e) {
			logger.warn(e);
		}

		chunkloader = new BlockChunkLoader();
		GameRegistry.registerBlock(chunkloader, "chunkloader");

		GameRegistry.registerTileEntity(TileChunkLoader.class, "TileChunkLoader");

		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoadingHandler());

	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
	}

	@Mod.EventHandler
	public void PostInit(FMLPostInitializationEvent event) {
	}
}
