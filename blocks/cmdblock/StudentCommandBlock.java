package com.dyn.item.blocks.cmdblock;

import java.util.Random;

import com.dyn.item.tileentity.TileEntityStudentCommandBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class StudentCommandBlock extends BlockContainer {
	public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");

	public StudentCommandBlock() {
		super(Material.iron, MapColor.adobeColor);
		setDefaultState(blockState.getBaseState().withProperty(TRIGGERED, Boolean.valueOf(false)));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TRIGGERED });
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityStudentCommandBlock();
	}

	@Override
	public int getComparatorInputOverride(World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof TileEntityStudentCommandBlock
				? ((TileEntityStudentCommandBlock) tileentity).getCommandBlockLogic().getSuccessCount() : 0;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (state.getValue(TRIGGERED).booleanValue()) {
			i |= 1;
		}

		return i;
	}

	/**
	 * The type of render function called. 3 for standard block models, 2 for
	 * TESR's, 1 for liquids, -1 is no render
	 */
	@Override
	public int getRenderType() {
		return 3;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TRIGGERED, Boolean.valueOf((meta & 1) > 0));
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof TileEntityStudentCommandBlock
				? ((TileEntityStudentCommandBlock) tileentity).getCommandBlockLogic().tryOpenEditCommandBlock(playerIn)
				: false;
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(TRIGGERED, Boolean.valueOf(false));
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow
	 * post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityStudentCommandBlock) {
			StudentCommandBlockLogic commandblocklogic = ((TileEntityStudentCommandBlock) tileentity)
					.getCommandBlockLogic();

			if (stack.hasDisplayName()) {
				commandblocklogic.setName(stack.getDisplayName());
			}

			if (!worldIn.isRemote) {
				commandblocklogic.setTrackOutput(worldIn.getGameRules().getBoolean("sendCommandFeedback"));
			}
		}
	}

	/**
	 * Called when a neighboring block changes.
	 */
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!worldIn.isRemote) {
			boolean flag = worldIn.isBlockPowered(pos);
			boolean flag1 = state.getValue(TRIGGERED).booleanValue();

			if (flag && !flag1) {
				worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(true)), 4);
				worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
			} else if (!flag && flag1) {
				worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(false)), 4);
			}
		}
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World worldIn) {
		return 1;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityStudentCommandBlock) {
			((TileEntityStudentCommandBlock) tileentity).getCommandBlockLogic().trigger(worldIn);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
	}
}