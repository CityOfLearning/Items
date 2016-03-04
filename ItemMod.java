package com.dyn.item;

import org.apache.logging.log4j.Logger;

import com.dyn.item.blocks.BlockChunkLoader;
import com.dyn.item.blocks.FlagBlock;
import com.dyn.item.blocks.TileChunkLoader;
import com.dyn.item.handlers.ChunkLoadingHandler;
import com.dyn.item.items.Flags;
import com.dyn.item.proxy.Proxy;
import com.dyn.item.reference.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ItemMod {
	@Mod.Instance(Reference.MOD_ID)
     public static ItemMod instance;

     @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
 	public static Proxy proxy;

     public static Item flags;
     public static Block flagBlock;
     public static Block chunkloader;
     
     public static Logger logger;

     @Mod.EventHandler
     public void preInit(FMLPreInitializationEvent event) {
    	 logger = event.getModLog();

 		Configuration configs = new Configuration(event.getSuggestedConfigurationFile());
 		try {
 			configs.load();
 		} catch (RuntimeException e) {
 			logger.warn(e);
 		}
     }

     @Mod.EventHandler
     public void init(FMLInitializationEvent event) {
    	 flags = new Flags();
         GameRegistry.registerItem(flags, "ctfFlags");
         
         flagBlock = new FlagBlock();
         
         GameRegistry.registerBlock(flagBlock, "ctfFlagBlocks");
         
         chunkloader = new BlockChunkLoader();
 		GameRegistry.registerBlock(chunkloader, "chunkloader");

 		GameRegistry.registerTileEntity(TileChunkLoader.class, "TileChunkLoader");

 		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoadingHandler());
         
         proxy.init();
     }

     @Mod.EventHandler
     public void postInit(FMLPostInitializationEvent event) {

     }
}