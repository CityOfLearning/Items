package com.dyn.item;

import com.dyn.item.blocks.FlagBlock;
import com.dyn.item.items.Flags;
import com.dyn.item.proxy.Proxy;
import com.dyn.item.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ItemMod {
	@Mod.Instance(Reference.MOD_ID)
     public static ItemMod instance;

     @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
 	public static Proxy proxy;

     public static Item flags;
     public static Block flagBlock;

     @Mod.EventHandler
     public void preInit(FMLPreInitializationEvent event) {
             flags = new Flags();
             GameRegistry.registerItem(flags, "ctfFlags");
             
             flagBlock = new FlagBlock();
             
             GameRegistry.registerBlock(flagBlock, "ctfFlagBlocks");
             
             proxy.init();
     }

     @Mod.EventHandler
     public void init(FMLInitializationEvent event) {
     }

     @Mod.EventHandler
     public void postInit(FMLPostInitializationEvent event) {

     }
}