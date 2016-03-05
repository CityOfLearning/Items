package com.dyn.item.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockChunkLoader extends BlockContainer implements ITileEntityProvider {

	public BlockChunkLoader() {
		super(Material.cloth);
		this.setBlockUnbreakable();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("chunk_loader");
	}

	protected BlockChunkLoader(Material materialIn) {
		super(materialIn);
		this.setBlockUnbreakable();
	}

	// Called when the block is placed or loaded client side to get the tile
	// entity for the block
	// Should return a new instance of the tile entity for the block
	// Alternatively - you can @Override hasTileEntity(IBlockState state) and
	// createTileEntity(World world, IBlockState state) instead.
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileChunkLoader();
	}
	// ** Returns the mobility information of the block, 0 = free, 1 = can't
	// push but can move over, 2 = total immobility and stop pistons

	@Override
	public int getMobilityFlag() {
		return 2;
	}

	// render using a BakedModel (mbe01_block_simple.json -->
	// mbe01_block_simple_model.json)
	// not strictly required because the default (super method) is 3.
	@Override
	public int getRenderType() {
		return 3;
	}

	// used by the renderer to control lighting and visibility of other blocks,
	// also by
	// (eg) wall or fence to control whether the fence joins itself to this
	// block
	// set to false because this block doesn't fill the entire 1x1x1 space
	@Override
	public boolean isFullCube() {
		return false;
	}

	// used by the renderer to control lighting and visibility of other blocks.
	// set to false because this block doesn't fill the entire 1x1x1 space
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/*
	 * @Override
	 *
	 * @SideOnly(Side.CLIENT) public IIcon getIcon(int par1, int par2) { return
	 * this.blockIcon; }
	 *
	 * @Override
	 *
	 * @SideOnly(Side.CLIENT) public void registerBlockIcons(IIconRegister
	 * par1IconRegister) { this.blockIcon =
	 * par1IconRegister.registerIcon("dyn:chunkloader"); }
	 */
}