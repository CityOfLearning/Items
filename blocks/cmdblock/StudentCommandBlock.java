package com.dyn.item.blocks.cmdblock;

import java.util.Random;

import com.dyn.item.tileentity.TileEntityStudentCommandBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class StudentCommandBlock extends BlockContainer {
	private static boolean blstate = false;

	public StudentCommandBlock() {
		super(Material.iron, MapColor.adobeColor);
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
		return blstate ? 1 : 0;
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
		return getDefaultState();
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
		return getDefaultState();
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

			if (flag && !StudentCommandBlock.blstate/* flag1 */) {
				StudentCommandBlock.blstate = true;
				worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
			} else if (!flag && StudentCommandBlock.blstate/* flag1 */) {
				StudentCommandBlock.blstate = false;
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
		}
	}
}