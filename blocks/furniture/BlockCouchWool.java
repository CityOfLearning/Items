//

//

package com.dyn.item.blocks.furniture;

import java.util.List;

import com.dyn.item.blocks.furniture.tiles.TileColorable;
import com.dyn.item.blocks.furniture.tiles.TileCouchWool;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import noppes.npcs.NoppesUtilServer;
import com.dyn.item.blocks.furniture.renderer.ITileRenderer;

public class BlockCouchWool extends BlockContainer implements ITileRenderer {
	public static final PropertyInteger DAMAGE;
	static {
		DAMAGE = PropertyInteger.create("damage", 0, 5);
	}

	private TileColorable renderTile;

	public BlockCouchWool() {
		super(Material.wood);
	}

	private boolean compareCornerTiles(final TileCouchWool tile, final BlockPos pos, final World world, final int meta,
			final boolean isLeft) {
		final IBlockState state = world.getBlockState(pos);
		if (state.getBlock() != this) {
			return false;
		}
		final int meta2 = state.getValue(BlockCouchWool.DAMAGE);
		if (meta2 != meta) {
			return false;
		}
		final TileEntity tile2 = world.getTileEntity(pos);
		final int rotation = (tile.rotation + (isLeft ? 3 : 1)) % 4;
		return ((tile2 != null) & (tile2 instanceof TileCouchWool)) && (((TileCouchWool) tile2).rotation == rotation);
	}

	private boolean compareTiles(final TileCouchWool tile, final BlockPos pos, final World world, final int meta) {
		final IBlockState state = world.getBlockState(pos);
		if (state.getBlock() != this) {
			return false;
		}
		final int meta2 = state.getValue(BlockCouchWool.DAMAGE);
		if (meta2 != meta) {
			return false;
		}
		final TileEntity tile2 = world.getTileEntity(pos);
		if ((tile2 == null) || !(tile2 instanceof TileCouchWool)) {
			return false;
		}
		final TileCouchWool couch = (TileCouchWool) tile2;
		int rotation = couch.rotation;
		if (tile.rotation == rotation) {
			return true;
		}
		if (couch.hasCornerLeft) {
			rotation += 3;
		} else if (couch.hasCornerRight) {
			++rotation;
		}
		rotation %= 4;
		return tile.rotation == rotation;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { BlockCouchWool.DAMAGE });
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int var2) {
		return new TileCouchWool();
	}

	@Override
	public int damageDropped(final IBlockState state) {
		return state.getValue(BlockCouchWool.DAMAGE);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos pos, final IBlockState state) {
		return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 1);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return damageDropped(state);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(BlockCouchWool.DAMAGE, meta);
	}

	@Override
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
		par3List.add(new ItemStack(par1, 1, 4));
		par3List.add(new ItemStack(par1, 1, 5));
	}

	@Override
	public TileColorable getTile() {
		if (renderTile == null) {
			renderTile = (TileColorable) createNewTileEntity(null, 0);
		}
		return renderTile;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(final World par1World, final BlockPos pos, final IBlockState state,
			final EntityPlayer player, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		final ItemStack item = player.inventory.getCurrentItem();
		if ((item == null) || (item.getItem() != Items.dye)) {
			return BlockChair.MountBlock(par1World, pos, player);
		}
		final TileColorable tile = (TileColorable) par1World.getTileEntity(pos);
		final int color = EnumDyeColor.byDyeDamage(item.getItemDamage()).getMetadata();
		if (tile.color != color) {
			NoppesUtilServer.consumeItemStack(1, player);
			tile.color = color;
			par1World.markBlockForUpdate(pos);
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(final World world, final BlockPos pos, final IBlockState state,
			final EntityLivingBase entity, final ItemStack stack) {
		int l = MathHelper.floor_double(((entity.rotationYaw * 4.0f) / 360.0f) + 0.5) & 0x3;
		l %= 4;
		world.setBlockState(pos, state.withProperty(BlockCouchWool.DAMAGE, stack.getItemDamage()), 2);
		final TileCouchWool tile = (TileCouchWool) world.getTileEntity(pos);
		tile.rotation = l;
		tile.color = 15 - stack.getItemDamage();
		updateModel(world, pos, tile);
		onNeighborBlockChange(world, pos.east(), state, this);
		onNeighborBlockChange(world, pos.west(), state, this);
		onNeighborBlockChange(world, pos.north(), state, this);
		onNeighborBlockChange(world, pos.south(), state, this);
		updateModel(world, pos, tile);
		world.markBlockForUpdate(pos);
	}

	@Override
	public void onNeighborBlockChange(final World worldObj, final BlockPos pos, final IBlockState state,
			final Block block) {
		if (worldObj.isRemote || (block != this)) {
			return;
		}
		final TileEntity tile = worldObj.getTileEntity(pos);
		if ((tile == null) || !(tile instanceof TileCouchWool)) {
			return;
		}
		updateModel(worldObj, pos, (TileCouchWool) tile);
		worldObj.markBlockForUpdate(pos);
	}

	private void updateModel(final World world, final BlockPos pos, final TileCouchWool tile) {
		if (world.isRemote) {
			return;
		}
		final int meta = tile.getBlockMetadata();
		if (tile.rotation == 0) {
			tile.hasCornerLeft = compareCornerTiles(tile, pos.north(), world, meta, true);
			tile.hasCornerRight = compareCornerTiles(tile, pos.north(), world, meta, false);
			tile.hasLeft = compareTiles(tile, pos.west(), world, meta);
			tile.hasRight = compareTiles(tile, pos.east(), world, meta);
		} else if (tile.rotation == 2) {
			tile.hasCornerLeft = compareCornerTiles(tile, pos.south(), world, meta, true);
			tile.hasCornerRight = compareCornerTiles(tile, pos.south(), world, meta, false);
			tile.hasLeft = compareTiles(tile, pos.east(), world, meta);
			tile.hasRight = compareTiles(tile, pos.west(), world, meta);
		} else if (tile.rotation == 1) {
			tile.hasCornerLeft = compareCornerTiles(tile, pos.east(), world, meta, true);
			tile.hasCornerRight = compareCornerTiles(tile, pos.east(), world, meta, false);
			tile.hasLeft = compareTiles(tile, pos.north(), world, meta);
			tile.hasRight = compareTiles(tile, pos.south(), world, meta);
		} else if (tile.rotation == 3) {
			tile.hasCornerLeft = compareCornerTiles(tile, pos.west(), world, meta, true);
			tile.hasCornerRight = compareCornerTiles(tile, pos.west(), world, meta, false);
			tile.hasLeft = compareTiles(tile, pos.south(), world, meta);
			tile.hasRight = compareTiles(tile, pos.north(), world, meta);
		}
	}
}
