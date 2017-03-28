package com.dyn.fixins;

import java.awt.Color;

import com.dyn.fixins.blocks.DynBlockManager;
import com.dyn.fixins.entity.crash.CrashTestEntity;
import com.dyn.fixins.entity.ghost.GhostEntity;
import com.dyn.fixins.items.DynItemManager;
import com.dyn.fixins.proxy.Proxy;
import com.dyn.fixins.reference.MetaData;
import com.dyn.fixins.reference.Reference;
import com.dyn.fixins.tab.DYNTab;
import com.rabbit.gui.utils.ColourHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:dyn|server;required-after:customnpcs")
public class DynFixinsMod {
	@Mod.Instance(Reference.MOD_ID)
	public static DynFixinsMod instance;

	public static CreativeTabs dynTab = new DYNTab();

	@SidedProxy(modId = Reference.MOD_ID, clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static Proxy proxy;

	public static void registerNewEntity(Class entityClass, String name, int id) {
		EntityRegistry.registerModEntity(entityClass, name, id, instance, 64, 3, false);
	}

	public static void registerNewEntity(Class entityClass, String name, int id, int egg1, int egg2) {
		EntityRegistry.registerModEntity(entityClass, name, id, instance, 64, 3, false, egg1, egg2);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		DynBlockManager.register();
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MetaData.init(event.getModMetadata());
		
		DynBlockManager.load();
		DynItemManager.load();

		registerNewEntity(CrashTestEntity.class, "dynCrashTestEntity", 1, ColourHelper.AWTColor2RGBInt(Color.BLACK),
				ColourHelper.AWTColor2RGBInt(Color.YELLOW));

		registerNewEntity(GhostEntity.class, "dynGhostEntity", 2, ColourHelper.AWTColor2RGBInt(Color.white),
				ColourHelper.AWTColor2RGBInt(Color.white));

		proxy.preInit();
	}
}