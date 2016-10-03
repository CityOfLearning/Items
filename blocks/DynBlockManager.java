package com.dyn.item.blocks;

import com.dyn.item.ItemMod;
import com.dyn.item.blocks.furniture.BlockBanner;
import com.dyn.item.blocks.furniture.BlockBarrel;
import com.dyn.item.blocks.furniture.BlockBeam;
import com.dyn.item.blocks.furniture.BlockBigSign;
import com.dyn.item.blocks.furniture.BlockBook;
import com.dyn.item.blocks.furniture.BlockCampfire;
import com.dyn.item.blocks.furniture.BlockCandle;
import com.dyn.item.blocks.furniture.BlockChair;
import com.dyn.item.blocks.furniture.BlockCouchWood;
import com.dyn.item.blocks.furniture.BlockCouchWool;
import com.dyn.item.blocks.furniture.BlockCrate;
import com.dyn.item.blocks.furniture.BlockCrystal;
import com.dyn.item.blocks.furniture.BlockLamp;
import com.dyn.item.blocks.furniture.BlockPedestal;
import com.dyn.item.blocks.furniture.BlockShelf;
import com.dyn.item.blocks.furniture.BlockSign;
import com.dyn.item.blocks.furniture.BlockStool;
import com.dyn.item.blocks.furniture.BlockTable;
import com.dyn.item.blocks.furniture.BlockTallLamp;
import com.dyn.item.blocks.furniture.BlockTombstone;
import com.dyn.item.blocks.furniture.BlockWallBanner;
import com.dyn.item.blocks.furniture.BlockWeaponRack;
import com.dyn.item.blocks.furniture.tiles.TileBanner;
import com.dyn.item.blocks.furniture.tiles.TileBarrel;
import com.dyn.item.blocks.furniture.tiles.TileBeam;
import com.dyn.item.blocks.furniture.tiles.TileBigSign;
import com.dyn.item.blocks.furniture.tiles.TileBook;
import com.dyn.item.blocks.furniture.tiles.TileCampfire;
import com.dyn.item.blocks.furniture.tiles.TileCandle;
import com.dyn.item.blocks.furniture.tiles.TileChair;
import com.dyn.item.blocks.furniture.tiles.TileCouchWood;
import com.dyn.item.blocks.furniture.tiles.TileCouchWool;
import com.dyn.item.blocks.furniture.tiles.TileCrate;
import com.dyn.item.blocks.furniture.tiles.TileLamp;
import com.dyn.item.blocks.furniture.tiles.TilePedestal;
import com.dyn.item.blocks.furniture.tiles.TileShelf;
import com.dyn.item.blocks.furniture.tiles.TileSign;
import com.dyn.item.blocks.furniture.tiles.TileStool;
import com.dyn.item.blocks.furniture.tiles.TileTable;
import com.dyn.item.blocks.furniture.tiles.TileTallLamp;
import com.dyn.item.blocks.furniture.tiles.TileTombstone;
import com.dyn.item.blocks.furniture.tiles.TileWallBanner;
import com.dyn.item.blocks.furniture.tiles.TileWeaponRack;
import com.dyn.item.items.ItemBlockLight;
import com.dyn.item.items.ItemNpcColored;
import com.dyn.item.tabs.FurnitureTab;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DynBlockManager {

	public static Block banner;
	public static Block wallBanner;
	public static Block tallLamp;
	public static Block blood;
	public static Block book;
	public static Block chair;
	public static Block crate;
	public static Block weaponsRack;
	public static Block pedestal;
	public static Block couchWool;
	public static Block couchWood;
	public static Block table;
	public static Block stool;
	public static Block bigsign;
	public static Block barrel;
	public static Block tombstone;
	public static Block shelf;
	public static Block sign;
	public static Block beam;
	public static Block lamp;
	public static Block campfire;
	public static Block candle;
	public static Block lamp_unlit;
	public static Block campfire_unlit;
	public static Block candle_unlit;

	public static FurnitureTab furniture;
	
	static {
		DynBlockManager.furniture = new FurnitureTab("Furniture");
	}

	public static void load() {
		GameRegistry.registerTileEntity(TileBanner.class, "TileNPCBanner");
		GameRegistry.registerTileEntity(TileWallBanner.class, "TileNPCWallBanner");
		GameRegistry.registerTileEntity(TileTallLamp.class, "TileNPCTallLamp");
		GameRegistry.registerTileEntity(TileChair.class, "TileNPCChair");
		GameRegistry.registerTileEntity(TileCrate.class, "TileNPCCrate");
		GameRegistry.registerTileEntity(TileWeaponRack.class, "TileNPCWeaponRack");
		GameRegistry.registerTileEntity(TileCouchWool.class, "TileNPCCouchWool");
		GameRegistry.registerTileEntity(TileCouchWood.class, "TileNPCCouchWood");
		GameRegistry.registerTileEntity(TileTable.class, "TileNPCTable");
		GameRegistry.registerTileEntity(TileLamp.class, "TileNPCLamp");
		GameRegistry.registerTileEntity(TileCandle.class, "TileNPCCandle");
		GameRegistry.registerTileEntity(TileStool.class, "TileNPCStool");
		GameRegistry.registerTileEntity(TileBigSign.class, "TileNPCBigSign");
		GameRegistry.registerTileEntity(TileBarrel.class, "TileNPCBarrel");
		GameRegistry.registerTileEntity(TileCampfire.class, "TileNPCCampfire");
		GameRegistry.registerTileEntity(TileTombstone.class, "TileNPCTombstone");
		GameRegistry.registerTileEntity(TileShelf.class, "TileNPCShelf");
		GameRegistry.registerTileEntity(TileSign.class, "TileNPCSign");
		GameRegistry.registerTileEntity(TileBeam.class, "TileNPCBeam");
		GameRegistry.registerTileEntity(TileBook.class, "TileNPCBook");
		GameRegistry.registerTileEntity(TilePedestal.class, "TileNPCPedestal");

		DynBlockManager.banner = new BlockBanner().setUnlocalizedName("npcBanner").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeMetal).setCreativeTab(furniture);
		DynBlockManager.wallBanner = new BlockWallBanner().setUnlocalizedName("npcWallBanner").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeMetal).setCreativeTab(furniture);
		DynBlockManager.tallLamp = new BlockTallLamp().setUnlocalizedName("npcTallLamp").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeMetal).setCreativeTab(furniture);
		DynBlockManager.chair = new BlockChair().setUnlocalizedName("npcChair").setHardness(5.0f).setResistance(10.0f)
				.setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.crate = new BlockCrate().setUnlocalizedName("npcCrate").setHardness(5.0f).setResistance(10.0f)
				.setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.weaponsRack = new BlockWeaponRack().setUnlocalizedName("npcWeaponRack").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.couchWool = new BlockCouchWool().setUnlocalizedName("npcCouchWool").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.table = new BlockTable().setUnlocalizedName("npcTable").setHardness(5.0f).setResistance(10.0f)
				.setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.couchWood = new BlockCouchWood().setUnlocalizedName("npcCouchWood").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.lamp = new BlockLamp(true).setUnlocalizedName("npcLamp").setHardness(5.0f).setResistance(10.0f)
				.setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.candle = new BlockCandle(true).setUnlocalizedName("npcCandle").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeWood);
		DynBlockManager.stool = new BlockStool().setUnlocalizedName("npcStool").setHardness(5.0f).setResistance(10.0f)
				.setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.bigsign = new BlockBigSign().setUnlocalizedName("npcBigSign").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.barrel = new BlockBarrel().setUnlocalizedName("npcBarrel").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.campfire = new BlockCampfire(true).setUnlocalizedName("npcCampfire").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeStone).setCreativeTab(furniture);
		DynBlockManager.tombstone = new BlockTombstone().setUnlocalizedName("npcTombstone").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeStone).setCreativeTab(furniture);
		DynBlockManager.shelf = new BlockShelf().setUnlocalizedName("npcShelf").setHardness(5.0f).setResistance(10.0f)
				.setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.sign = new BlockSign().setUnlocalizedName("npcSign").setHardness(5.0f).setResistance(10.0f)
				.setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.beam = new BlockBeam().setUnlocalizedName("npcBeam").setHardness(5.0f).setResistance(10.0f)
				.setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.book = new BlockBook().setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.pedestal = new BlockPedestal().setUnlocalizedName("npcPedestal").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeWood).setCreativeTab(furniture);
		DynBlockManager.campfire_unlit = new BlockCampfire(false).setUnlocalizedName("npcCampfire").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeStone);
		DynBlockManager.lamp_unlit = new BlockLamp(false).setUnlocalizedName("npcLamp").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeWood);
		DynBlockManager.candle_unlit = new BlockCandle(false).setUnlocalizedName("npcCandle").setHardness(5.0f)
				.setResistance(10.0f).setStepSound(Block.soundTypeWood);
		ItemMod.proxy.registerBlock(DynBlockManager.banner, "npcBanner", 4, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.wallBanner, "npcWallBanner", 4, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.tallLamp, "npcTallLamp", 4, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.chair, "npcChair", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.crate, "npcCrate", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.weaponsRack, "npcWeaponRack", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.couchWool, "npcCouchWool", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.couchWood, "npcCouchWood", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.table, "npcTable", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.stool, "npcStool", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.bigsign, "npcBigSign", 0, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.barrel, "npcBarrel", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.tombstone, "npcTombstone", 2, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.shelf, "npcShelf", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.sign, "npcSign", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.beam, "npcBeam", 5, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.book, "npcBook", 0, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.pedestal, "npcPedestal", 4, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.campfire, "npcCampfire", 0, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.candle, "npcCandle", 0, ItemBlockLight.class);
		ItemMod.proxy.registerBlock(DynBlockManager.lamp, "npcLamp", 0, ItemBlockLight.class);
		ItemMod.proxy.registerBlock(DynBlockManager.campfire_unlit, "npcCampfireUnlit", 0, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.candle_unlit, "npcCandleUnlit", 0, ItemBlock.class);
		ItemMod.proxy.registerBlock(DynBlockManager.lamp_unlit, "npcLampUnlit", 0, ItemBlock.class);

		DynBlockManager.furniture.setTabIconItem(Item.getItemFromBlock(DynBlockManager.couchWool));
	}
}