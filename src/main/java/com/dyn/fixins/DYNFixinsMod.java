package com.dyn.fixins;

import java.awt.Color;
import java.io.File;

import org.apache.logging.log4j.Logger;

import com.dyn.fixins.blocks.DynBlockManager;
import com.dyn.fixins.items.DynItemManager;
import com.dyn.fixins.network.NetworkManager;
import com.dyn.fixins.proxy.Proxy;
import com.dyn.fixins.reference.MetaData;
import com.dyn.fixins.reference.Reference;
import com.dyn.fixins.tab.DYNTab;
import com.dyn.fixins.util.PlayerAccessLevel;
import com.rabbit.gui.utils.ColourHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "after:rabbit-gui")
public class DYNFixinsMod {
	@Mod.Instance(Reference.MOD_ID)
	public static DYNFixinsMod instance;
	public static Logger logger;
	
	public static CreativeTabs dynTab = new DYNTab();

	@SidedProxy(modId = Reference.MOD_ID, clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static Proxy proxy;
	public static PlayerAccessLevel accessLevel = PlayerAccessLevel.ADMIN;

	public static void registerNewEntity(Class entityClass, String name, int id) {
		EntityRegistry.registerModEntity(entityClass, name, id, instance, 64, 3, false);
	}

	public static void registerNewEntity(Class entityClass, String name, int id, int egg1, int egg2) {
		EntityRegistry.registerModEntity(entityClass, name, id, instance, 64, 3, false, egg1, egg2);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		DynBlockManager.register();

		MinecraftForge.EVENT_BUS.register(this);

		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MetaData.init(event.getModMetadata());
		
		NetworkManager.registerPackets();
		NetworkManager.registerMessages();
		
		logger = event.getModLog();

		DynBlockManager.load();
		DynItemManager.load();
		proxy.preInit();
	}
	
	@SubscribeEvent
	public void loginEvent(PlayerEvent.PlayerLoggedInEvent event) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			if(net.minecraft.client.Minecraft.getMinecraft().playerController.getCurrentGameType() == GameType.CREATIVE) {
				DYNFixinsMod.accessLevel = PlayerAccessLevel.ADMIN;
			}
		} else {
			String[] players = MinecraftServer.getServer().getConfigurationManager().getOppedPlayerNames();
			for(String player : players) {
				if(event.player.getName().equals(player)) {
					DYNFixinsMod.accessLevel = PlayerAccessLevel.ADMIN;
				}
			}
		}
	}
}