package com.dyn.item.proxy;

import com.dyn.item.blocks.DynBlockManager;
import com.dyn.item.blocks.cmdblock.StudentCommandBlockLogic;
import com.dyn.item.blocks.furniture.BlockBanner;
import com.dyn.item.blocks.furniture.BlockCouchWood;
import com.dyn.item.blocks.furniture.BlockCouchWool;
import com.dyn.item.blocks.furniture.BlockLightable;
import com.dyn.item.blocks.furniture.BlockRotated;
import com.dyn.item.blocks.furniture.BlockTallLamp;
import com.dyn.item.blocks.furniture.BlockTombstone;
import com.dyn.item.blocks.furniture.BlockWallBanner;
import com.dyn.item.blocks.furniture.BlockWeaponRack;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockBannerRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockBarrelRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockBeamRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockBigSignRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockBookRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockCampfireRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockCandleRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockChairRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockCouchWoodRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockCouchWoolRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockCrateRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockLampRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockPedestalRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockShelfRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockSignRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockStoolRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockTableRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockTallLampRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockTombstoneRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockWallBannerRenderer;
import com.dyn.item.blocks.furniture.renderer.blocks.BlockWeaponRackRenderer;
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
import com.dyn.item.gui.command.StudentComamndGui;
import com.dyn.item.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Client implements Proxy {

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileBanner.class, new BlockBannerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileWallBanner.class, new BlockWallBannerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileTallLamp.class, new BlockTallLampRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileChair.class, new BlockChairRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileWeaponRack.class, new BlockWeaponRackRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrate.class, new BlockCrateRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCouchWool.class, new BlockCouchWoolRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCouchWood.class, new BlockCouchWoodRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileTable.class, new BlockTableRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCandle.class, new BlockCandleRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileLamp.class, new BlockLampRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileStool.class, new BlockStoolRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBigSign.class, new BlockBigSignRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBarrel.class, new BlockBarrelRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCampfire.class, new BlockCampfireRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileTombstone.class, new BlockTombstoneRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileShelf.class, new BlockShelfRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSign.class, new BlockSignRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBeam.class, new BlockBeamRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBook.class, new BlockBookRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePedestal.class, new BlockPedestalRenderer());
	}

	@Override
	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic) {
		Minecraft.getMinecraft().displayGuiScreen(new StudentComamndGui(cmdBlockLogic));
	}

	@Override
	public void registerBlock(Block block, String name, int meta, Class<? extends ItemBlock> itemclass) {
		this.registerBlock(block, name, meta, itemclass, false);
	}

	@Override
	public void registerBlock(Block block, String name, int meta, Class<? extends ItemBlock> itemclass,
			boolean seperateMetadata) {
		GameRegistry.registerBlock(block, itemclass, name);
		registerItems(GameRegistry.findItem(Reference.MOD_ID, name), name, meta, seperateMetadata);
	}

	@Override
	public void registerBlockItem(Block block) {
		String blockName = block.getUnlocalizedName().replace("tile.", "");
		Item blockItem = GameRegistry.findItem(Reference.MOD_ID, blockName);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MOD_ID + ":" + blockName,
				"inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(blockItem, 0, itemModelResourceLocation);
	}

	@Override
	public void registerItem(Item item, String name, int meta) {
		if (name.contains("item.")) {
			name = name.replace("item.", "");
		}
		ModelResourceLocation location = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, location);
	}

	public void registerItems(final Item item, final String name, final int meta, final boolean seperate) {
		if (item != null) {
			if (meta > 0) {
				item.setHasSubtypes(true);
				for (int i = 0; i <= meta; ++i) {
					String metaname = name;
					if (seperate) {
						metaname = metaname + "_" + i;
						ModelBakery.addVariantName(item, new String[] { Reference.MOD_ID + ":" + metaname });
					}
					registerItem(item, metaname, i);
				}
			} else {
				registerItem(item, name, 0);
			}
		}
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}

	private void blockIgnoreBlockstate(Block block, IProperty... properties) {
		ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(properties).build());
	}

	@Override
	public void preInit() {
		blockIgnoreBlockstate(DynBlockManager.pedestal, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.beam, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.crate, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.book, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.stool, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.chair, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.sign, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.barrel, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.couchWood, BlockCouchWood.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.couchWool, BlockCouchWool.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.tombstone, BlockTombstone.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.bigsign, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.table, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.shelf, BlockRotated.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.wallBanner, BlockWallBanner.DAMAGE);
		blockIgnoreBlockstate(DynBlockManager.banner, BlockBanner.DAMAGE, BlockBanner.TOP);
		blockIgnoreBlockstate(DynBlockManager.weaponsRack, BlockWeaponRack.DAMAGE, BlockWeaponRack.IS_TOP);
		blockIgnoreBlockstate(DynBlockManager.tallLamp, BlockTallLamp.DAMAGE, BlockTallLamp.IS_TOP);
		blockIgnoreBlockstate(DynBlockManager.lamp, BlockRotated.DAMAGE, BlockLightable.LIT);
		blockIgnoreBlockstate(DynBlockManager.lamp_unlit, BlockRotated.DAMAGE, BlockLightable.LIT);
		blockIgnoreBlockstate(DynBlockManager.candle, BlockRotated.DAMAGE, BlockLightable.LIT);
		blockIgnoreBlockstate(DynBlockManager.candle_unlit, BlockRotated.DAMAGE, BlockLightable.LIT);
		blockIgnoreBlockstate(DynBlockManager.campfire, BlockRotated.DAMAGE, BlockLightable.LIT);
		blockIgnoreBlockstate(DynBlockManager.campfire_unlit, BlockRotated.DAMAGE, BlockLightable.LIT);
	}
}