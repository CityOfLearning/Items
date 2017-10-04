package com.dyn.fixins.items;

import com.dyn.fixins.DYNFixinsMod;
import com.dyn.fixins.blocks.DynBlockManager;
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
		DYNFixinsMod.proxy.registerItem(coinWood = new GenericItem(), "npcCoinWooden");
		DYNFixinsMod.proxy.registerItem(coinStone = new GenericItem(), "npcCoinStone");
		DYNFixinsMod.proxy.registerItem(coinBronze = new GenericItem(), "npcCoinBronze");
		DYNFixinsMod.proxy.registerItem(coinIron = new GenericItem(), "npcCoinIron");
		DYNFixinsMod.proxy.registerItem(coinGold = new GenericItem(), "npcCoinGold");
		DYNFixinsMod.proxy.registerItem(coinDiamond = new GenericItem(), "npcCoinDiamond");
		DYNFixinsMod.proxy.registerItem(coinEmerald = new GenericItem(), "npcCoinEmerald");
		DYNFixinsMod.proxy.registerItem(old_coin = new GenericItem(), "npcAncientCoin");
		DYNFixinsMod.proxy.registerItem(crystal = new GenericItem(), "npcCrystal");

		DYNFixinsMod.proxy.registerItem(dynLogo = new GenericItem().setCreativeTab(null), "dyn_logo");

		DYNFixinsMod.proxy.registerItem(key1 = new GenericItem(), "npcKey");
		DYNFixinsMod.proxy.registerItem(key2 = new GenericItem(), "npcKey2");
		final Item sapphire = new GenericItem().setUnlocalizedName("npcSaphire")
				.setCreativeTab(CreativeTabs.tabMaterials);
		final Item amethyst = new GenericItem().setUnlocalizedName("npcAmethyst")
				.setCreativeTab(CreativeTabs.tabMaterials);
		DYNFixinsMod.proxy.registerItem(sapphire, "npcSaphire");
		DYNFixinsMod.proxy.registerItem(amethyst, "npcAmethyst");
		OreDictionary.registerOre("gemSaphire", sapphire);
		OreDictionary.registerOre("gemAmethyst", amethyst);
		DYNFixinsMod.proxy.registerItem(bronze_ingot = new GenericItem().setCreativeTab(CreativeTabs.tabMaterials),
				"npcBronzeIngot");
		DYNFixinsMod.proxy.registerItem(demonic_ingot = new GenericItem().setCreativeTab(CreativeTabs.tabMaterials),
				"npcDemonicIngot");
		DYNFixinsMod.proxy.registerItem(mithril_ingot = new GenericItem().setCreativeTab(CreativeTabs.tabMaterials),
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

		DYNFixinsMod.proxy.registerItem(
				delayBlockItem = new ItemReed(DynBlockManager.delayBlockOff).setCreativeTab(DYNFixinsMod.dynTab),
				"delay_diode");

		DYNFixinsMod.proxy.registerItem(
				latchBlockItem = new ItemReed(DynBlockManager.latchBLock).setCreativeTab(DYNFixinsMod.dynTab),
				"latch_diode");
	}
}