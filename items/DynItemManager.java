package com.dyn.fixins.items;

import com.dyn.DYNServerMod;
import com.dyn.fixins.DynFixinsMod;
import com.dyn.fixins.blocks.DynBlockManager;
import com.dyn.utils.PlayerAccessLevel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemReed;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class DynItemManager {

	public static Item coinWood;
	public static Item coinStone;
	public static Item coinIron;
	public static Item coinGold;
	public static Item coinDiamond;
	public static Item coinBronze;
	public static Item coinEmerald;
	public static Item bronze_ingot;
	public static Item demonic_ingot;
	public static Item mithril_ingot;
	public static Item key1;
	public static Item key2;
	public static Item old_coin;
	public static Item crystal;

	public static Item schematic;
	public static Item achMedal;
	public static Item delayBlockItem;

	public static Item dynLogo;
	public static Item latchBlockItem;
	public static Item manual;

	public static void load() {
		final Item.ToolMaterial bronze = EnumHelper.addToolMaterial("BRONZE", 2, 170, 5.0f, 2.0f, 15);
		final Item.ToolMaterial emerald = EnumHelper.addToolMaterial("REALEMERALD", 3, 1000, 8.0f, 4.0f, 10);
		final Item.ToolMaterial demonic = EnumHelper.addToolMaterial("DEMONIC", 3, 100, 8.0f, 6.0f, 22);
		final Item.ToolMaterial frost = EnumHelper.addToolMaterial("FROST", 2, 59, 6.0f, 3.0f, 5);
		final Item.ToolMaterial mithril = EnumHelper.addToolMaterial("MITHRIL", 3, 3000, 8.0f, 3.0f, 10);
		final ItemArmor.ArmorMaterial armorMithril = EnumHelper.addArmorMaterial("MITHRIL", "", 40,
				new int[] { 3, 8, 6, 3 }, 20);
		EnumHelper.addArmorMaterial("BRONZE", "", 7, new int[] { 2, 6, 5, 2 }, 20);
		EnumHelper.addArmorMaterial("EMERALD", "", 35, new int[] { 5, 7, 4, 5 }, 5);
		DynFixinsMod.proxy.registerItem(coinWood = new GenericItem(), "npcCoinWooden");
		DynFixinsMod.proxy.registerItem(coinStone = new GenericItem(), "npcCoinStone");
		DynFixinsMod.proxy.registerItem(coinBronze = new GenericItem(), "npcCoinBronze");
		DynFixinsMod.proxy.registerItem(coinIron = new GenericItem(), "npcCoinIron");
		DynFixinsMod.proxy.registerItem(coinGold = new GenericItem(), "npcCoinGold");
		DynFixinsMod.proxy.registerItem(coinDiamond = new GenericItem(), "npcCoinDiamond");
		DynFixinsMod.proxy.registerItem(coinEmerald = new GenericItem(), "npcCoinEmerald");
		DynFixinsMod.proxy.registerItem(old_coin = new GenericItem(), "npcAncientCoin");
		DynFixinsMod.proxy.registerItem(crystal = new GenericItem(), "npcCrystal");

		DynFixinsMod.proxy.registerItem(dynLogo = new GenericItem().setCreativeTab(null), "dyn_logo");

		DynFixinsMod.proxy.registerItem(key1 = new GenericItem(), "npcKey");
		DynFixinsMod.proxy.registerItem(key2 = new GenericItem(), "npcKey2");
		final Item sapphire = new GenericItem().setUnlocalizedName("npcSaphire")
				.setCreativeTab(CreativeTabs.tabMaterials);
		final Item amethyst = new GenericItem().setUnlocalizedName("npcAmethyst")
				.setCreativeTab(CreativeTabs.tabMaterials);
		DynFixinsMod.proxy.registerItem(sapphire, "npcSaphire");
		DynFixinsMod.proxy.registerItem(amethyst, "npcAmethyst");
		OreDictionary.registerOre("gemSaphire", sapphire);
		OreDictionary.registerOre("gemAmethyst", amethyst);
		DynFixinsMod.proxy.registerItem(bronze_ingot = new GenericItem().setCreativeTab(CreativeTabs.tabMaterials),
				"npcBronzeIngot");
		DynFixinsMod.proxy.registerItem(demonic_ingot = new GenericItem().setCreativeTab(CreativeTabs.tabMaterials),
				"npcDemonicIngot");
		DynFixinsMod.proxy.registerItem(mithril_ingot = new GenericItem().setCreativeTab(CreativeTabs.tabMaterials),
				"npcMithrilIngot");
		armorMithril.customCraftingMaterial = DynItemManager.mithril_ingot;
		bronze.customCraftingMaterial = DynItemManager.bronze_ingot;
		emerald.customCraftingMaterial = Items.emerald;
		demonic.customCraftingMaterial = DynItemManager.demonic_ingot;
		frost.customCraftingMaterial = Item.getItemFromBlock(Blocks.ice);
		mithril.customCraftingMaterial = DynItemManager.mithril_ingot;
		OreDictionary.registerOre("ingotBronze", DynItemManager.bronze_ingot);
		OreDictionary.registerOre("ingotDemonic", DynItemManager.demonic_ingot);
		OreDictionary.registerOre("ingotMithril", DynItemManager.mithril_ingot);

		DynFixinsMod.proxy.registerItem(schematic = new ItemSchematic(), "schematic");
		DynFixinsMod.proxy.registerItem(achMedal = new ItemAchievementMedal(), "ach_medal");
		// DynFixinsMod.proxy.registerItemModels(achMedal, "ach_medal", 1);
		// DynFixinsMod.proxy.registerItemModels(achMedal, "ach_medal", 2);
		// DynFixinsMod.proxy.registerItemModels(achMedal, "ach_medal", 3);
		// DynFixinsMod.proxy.registerItemModels(achMedal, "ach_medal", 4);

		DynFixinsMod.proxy.registerItem(
				delayBlockItem = new ItemReed(DynBlockManager.delayBlockOff).setCreativeTab(DynFixinsMod.dynTab),
				"delay_diode");

		DynFixinsMod.proxy.registerItem(
				latchBlockItem = new ItemReed(DynBlockManager.latchBLock).setCreativeTab(DynFixinsMod.dynTab),
				"latch_diode");

		manual = new ItemReferenceManual().setUnlocalizedName("dyn_robot_manual");
		GameRegistry.registerItem(manual, "dyn_robot_manual");
		DynFixinsMod.proxy.registerItemModels(manual, manual.getUnlocalizedName(), 0);

		if (DYNServerMod.developmentEnvironment || (DYNServerMod.accessLevel != PlayerAccessLevel.STUDENT)) {
			schematic.setCreativeTab(DynFixinsMod.dynTab);
			achMedal.setCreativeTab(DynFixinsMod.dynTab);
			manual.setCreativeTab(DynFixinsMod.dynTab);
		}
	}
}