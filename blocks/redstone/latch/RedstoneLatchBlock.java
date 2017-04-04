package com.dyn.fixins.blocks.redstone.latch;

import java.util.Random;

import com.dyn.fixins.items.DynItemManager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RedstoneLatchBlock extends BlockDirectional {
	// determines if the delay is how long the signal takes to
	// turn on or turn off
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static final PropertyBool LOCKED = PropertyBool.create("locked");

	public RedstoneLatchBlock() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
				.withProperty(LOCKED, Boolean.valueOf(false)).withProperty(POWERED, Boolean.valueOf(false)));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		notifyNeighbors(worldIn, pos, state);
	}

	protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING);
		BlockPos blockpos = pos.offset(enumfacing);
		int i = worldIn.getRedstonePower(blockpos, enumfacing);

		if (i >= 15) {
			return i;
		} else {
			IBlockState iblockstate = worldIn.getBlockState(blockpos);
			return Math.max(i, iblockstate.getBlock() == Blocks.redstone_wire
					? iblockstate.getValue(BlockRedstoneWire.POWER).intValue() : 0);
		}
	}

	public boolean canBlockStay(World worldIn, BlockPos pos) {
		return World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, LOCKED, POWERED });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World worldIn, BlockPos pos) {
		return DynItemManager.delayBlockItem;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return DynItemManager.delayBlockItem;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();
		i = i | ((state.getValue(LOCKED) ? 1 : 0) << 2);
		i = i | ((state.getValue(POWERED) ? 1 : 0) << 3);
		return i;
	}

	protected int getPowerOnSide(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		return (block == Blocks.redstone_wire ? iblockstate.getValue(BlockRedstoneWire.POWER).intValue()
				: worldIn.getStrongPower(pos, side));
	}

	protected int getPowerOnSides(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING);
		EnumFacing enumfacing1 = enumfacing.rotateY();
		EnumFacing enumfacing2 = enumfacing.rotateYCCW();
		return Math.max(getPowerOnSide(worldIn, pos.offset(enumfacing1), enumfacing1),
				getPowerOnSide(worldIn, pos.offset(enumfacing2), enumfacing2));
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta))
				.withProperty(LOCKED, Boolean.valueOf((meta >> 2) == 1 ? true : false))
				.withProperty(POWERED, Boolean.valueOf((meta >> 3) == 1 ? true : false));
	}

	@Override
	public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return getWeakPower(worldIn, pos, state, side);
	}

	@Override
	public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return !state.getValue(POWERED) ? 0 : (state.getValue(FACING) == side ? 15 : 0);
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	protected void notifyNeighbors(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING);
		BlockPos blockpos = pos.offset(enumfacing.getOpposite());
		if (net.minecraftforge.event.ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.getBlockState(pos),
				java.util.EnumSet.of(enumfacing.getOpposite())).isCanceled()) {
			return;
		}
		worldIn.notifyBlockOfStateChange(blockpos, this);
		worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		notifyNeighbors(worldIn, pos, state);
	}

	/**
	 * Called when a player destroys this Block
	 */
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getValue(POWERED)) {
			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
			}
		}

		super.onBlockDestroyedByPlayer(worldIn, pos, state);
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow
	 * post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (shouldBePowered(worldIn, pos, state)) {
			worldIn.scheduleUpdate(pos, this, 1);
		}
	}

	/**
	 * Called when a neighboring block changes.
	 */
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (canBlockStay(worldIn, pos)) {
			updateState(worldIn, pos, state);
		} else {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);

			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(POWERED)) {
			EnumFacing enumfacing = state.getValue(FACING);
			double d0 = pos.getX() + 0.5F + ((rand.nextFloat() - 0.5F) * 0.2D);
			double d1 = pos.getY() + 0.4F + ((rand.nextFloat() - 0.5F) * 0.2D);
			double d2 = pos.getZ() + 0.5F + ((rand.nextFloat() - 0.5F) * 0.2D);
			float f = -5.0F;

			if (rand.nextBoolean()) {
				f = (5 * 2) - 1;
			}

			f = f / 16.0F;
			double d3 = f * enumfacing.getFrontOffsetX();
			double d4 = f * enumfacing.getFrontOffsetZ();
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
		return calculateInputStrength(worldIn, pos, state) > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		return side.getAxis() != EnumFacing.Axis.Y;
	}

	protected boolean shouldUnlock(World worldIn, BlockPos pos, IBlockState state) {
		return getPowerOnSides(worldIn, pos, state) > 0;
	}

	protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
		boolean flag = shouldBePowered(worldIn, pos, state);

		if (state.getValue(POWERED) && state.getValue(LOCKED)) {
			if (shouldUnlock(worldIn, pos, state)) {
				worldIn.setBlockState(pos, state.withProperty(LOCKED, false).withProperty(POWERED, flag), 2);
				return;
			}
		}

		if ((state.getValue(POWERED) && !flag) || (!state.getValue(POWERED) && flag)) {
			if (flag) {
				worldIn.setBlockState(pos, state.withProperty(POWERED, flag).withProperty(LOCKED, true), 2);
			} else {
				if (!state.getValue(LOCKED)) {
					worldIn.setBlockState(pos, state.withProperty(POWERED, flag), 2);
				}
			}
		}
	}
}