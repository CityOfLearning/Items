package com.dyn.fixins.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class DynItemManager {

	public static Item coinWood;
	public static Item coinStone;
	public static Item coinIron;
	public static Item coinGold;
	public static Item coinDiamond;
	public static Item coinBronze;
	public static Item coinEmerald;
	public static Item moneyBag;
	public static Item orb;
	public static Item bronze_ingot;
	public static Item demonic_ingot;
	public static Item mithril_ingot;
	public static Item key1;
	public static Item key2;
	public static Item old_coin;
	public static Item crystal;

	public static void load() {
		final Item.ToolMaterial bronze = EnumHelper.addToolMaterial("BRONZE", 2, 170, 5.0f, 2.0f, 15);
		final Item.ToolMaterial emerald = EnumHelper.addToolMaterial("REALEMERALD", 3, 1000, 8.0f, 4.0f, 10);
		final Item.ToolMaterial demonic = EnumHelper.addToolMaterial("DEMONIC", 3, 100, 8.0f, 6.0f, 22);
		final Item.ToolMaterial frost = EnumHelper.addToolMaterial("FROST", 2, 59, 6.0f, 3.0f, 5);
		final Item.ToolMaterial mithril = EnumHelper.addToolMaterial("MITHRIL", 3, 3000, 8.0f, 3.0f, 10);
		DynItemManager.orb = new ItemOrb(26937).setUnlocalizedName("npcOrb");
		new ItemOrb(26939).setUnlocalizedName("npcBrokenOrb");
		final ItemArmor.ArmorMaterial armorMithril = EnumHelper.addArmorMaterial("MITHRIL", "", 40,
				new int[] { 3, 8, 6, 3 }, 20);
		EnumHelper.addArmorMaterial("BRONZE", "", 7, new int[] { 2, 6, 5, 2 }, 20);
		EnumHelper.addArmorMaterial("EMERALD", "", 35, new int[] { 5, 7, 4, 5 }, 5);
		DynItemManager.coinWood = new GenericItem(26717).setUnlocalizedName("npcCoinWooden");
		DynItemManager.coinStone = new GenericItem(26718).setUnlocalizedName("npcCoinStone");
		DynItemManager.coinBronze = new GenericItem(26719).setUnlocalizedName("npcCoinBronze");
		DynItemManager.coinIron = new GenericItem(26720).setUnlocalizedName("npcCoinIron");
		DynItemManager.coinGold = new GenericItem(26721).setUnlocalizedName("npcCoinGold");
		DynItemManager.coinDiamond = new GenericItem(26722).setUnlocalizedName("npcCoinDiamond");
		DynItemManager.coinEmerald = new GenericItem(26723).setUnlocalizedName("npcCoinEmerald");
		DynItemManager.old_coin = new GenericItem().setUnlocalizedName("npcAncientCoin");
		DynItemManager.crystal = new GenericItem(26954).setUnlocalizedName("npcCrystal");

		DynItemManager.key1 = new GenericItem(26964).setUnlocalizedName("npcKey");
		DynItemManager.key2 = new GenericItem(26965).setUnlocalizedName("npcKey2");
		final Item sapphire = new GenericItem(26970).setUnlocalizedName("npcSaphire")
				.setCreativeTab(CreativeTabs.tabMaterials);
		final Item ruby = new GenericItem(26971).setUnlocalizedName("npcRuby")
				.setCreativeTab(CreativeTabs.tabMaterials);
		final Item amethyst = new GenericItem(26972).setUnlocalizedName("npcAmethyst")
				.setCreativeTab(CreativeTabs.tabMaterials);
		OreDictionary.registerOre("gemSaphire", sapphire);
		OreDictionary.registerOre("gemRuby", ruby);
		OreDictionary.registerOre("gemAmethyst", amethyst);
		DynItemManager.bronze_ingot = new GenericItem(26973).setUnlocalizedName("npcBronzeIngot")
				.setCreativeTab(CreativeTabs.tabMaterials);
		DynItemManager.demonic_ingot = new GenericItem(26973).setUnlocalizedName("npcDemonicIngot")
				.setCreativeTab(CreativeTabs.tabMaterials);
		DynItemManager.mithril_ingot = new GenericItem(26973).setUnlocalizedName("npcMithrilIngot")
				.setCreativeTab(CreativeTabs.tabMaterials);
		armorMithril.customCraftingMaterial = DynItemManager.mithril_ingot;
		bronze.customCraftingMaterial = DynItemManager.bronze_ingot;
		emerald.customCraftingMaterial = Items.emerald;
		demonic.customCraftingMaterial = DynItemManager.demonic_ingot;
		frost.customCraftingMaterial = Item.getItemFromBlock(Blocks.ice);
		mithril.customCraftingMaterial = DynItemManager.mithril_ingot;
		OreDictionary.registerOre("ingotBronze", DynItemManager.bronze_ingot);
		OreDictionary.registerOre("ingotDemonic", DynItemManager.demonic_ingot);
		OreDictionary.registerOre("ingotMithril", DynItemManager.mithril_ingot);
	}
}