//

//

package com.dyn.item.blocks.furniture;

import java.util.List;

import com.dyn.item.blocks.furniture.tiles.TileBigSign;
import com.dyn.item.blocks.furniture.tiles.TileTombstone;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcsPermissions;
import noppes.npcs.NoppesUtilServer;
import com.dyn.item.blocks.furniture.renderer.ITileRenderer;
import noppes.npcs.constants.EnumGuiType;

public class BlockTombstone extends BlockContainer implements ITileRenderer {
	public static final PropertyInteger DAMAGE;
	static {
		DAMAGE = PropertyInteger.create("damage", 0, 6);
	}

	private TileEntity renderTile;

	public BlockTombstone() {
		super(Blocks.cobblestone.getMaterial());
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { BlockTombstone.DAMAGE });
	}

	@Override
	public TileEntity createNewTileEntity(final World var1, final int var2) {
		return new TileTombstone();
	}

	@Override
	public int damageDropped(final IBlockState state) {
		return state.getValue(BlockTombstone.DAMAGE);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		return damageDropped(state);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(BlockTombstone.DAMAGE, meta);
	}

	@Override
	public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
	}

	@Override
	public TileEntity getTile() {
		if (renderTile == null) {
			renderTile = createNewTileEntity(null, 0);
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

	public int maxRotation() {
		return 4;
	}

	@Override
	public boolean onBlockActivated(final World par1World, final BlockPos pos, final IBlockState state,
			final EntityPlayer player, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (par1World.isRemote) {
			return false;
		}
		final ItemStack currentItem = player.inventory.getCurrentItem();
		if ((currentItem == null) || (currentItem.getItem() != CustomItems.wand)
				|| !CustomNpcsPermissions.hasPermission(player, CustomNpcsPermissions.EDIT_BLOCKS)) {
			return false;
		}
		final TileBigSign tile = (TileBigSign) par1World.getTileEntity(pos);
		if (tile.getBlockMetadata() >= 2) {
			return false;
		}
		tile.canEdit = true;
		NoppesUtilServer.sendOpenGui(player, EnumGuiType.BigSign, null, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void onBlockPlacedBy(final World world, final BlockPos pos, final IBlockState state,
			final EntityLivingBase entity, final ItemStack stack) {
		int l = MathHelper.floor_double(((entity.rotationYaw * maxRotation()) / 360.0f) + 0.5) & (maxRotation() - 1);
		l %= maxRotation();
		final TileTombstone tile = (TileTombstone) world.getTileEntity(pos);
		tile.rotation = l;
		world.setBlockState(pos, state.withProperty(BlockTombstone.DAMAGE, stack.getItemDamage()), 2);
		if ((entity instanceof EntityPlayer) && !world.isRemote && (stack.getItemDamage() < 2)) {
			NoppesUtilServer.sendOpenGui((EntityPlayer) entity, EnumGuiType.BigSign, null, pos.getX(), pos.getY(),
					pos.getZ());
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess world, final BlockPos pos) {
		final TileEntity tileentity = world.getTileEntity(pos);
		if (!(tileentity instanceof TileTombstone)) {
			super.setBlockBoundsBasedOnState(world, pos);
			return;
		}
		final TileTombstone tile = (TileTombstone) tileentity;
		if ((tile.rotation % 2) == 0) {
			setBlockBounds(0.0f, 0.0f, 0.3f, 1.0f, 1.0f, 0.7f);
		} else {
			setBlockBounds(0.3f, 0.0f, 0.0f, 0.7f, 1.0f, 1.0f);
		}
	}
}
