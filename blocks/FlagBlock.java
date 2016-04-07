package com.dyn.item.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class FlagBlock extends BlockBanner {

	public FlagBlock() {
		super();
		setCreativeTab(CreativeTabs.tabMisc);
		setHardness(0.1F);
		setResistance(6000000.0F);
		setStepSound(soundTypeCloth);
		setUnlocalizedName("ctf_flags");

		setDefaultState(blockState.getBaseState().withProperty(ROTATION, Integer.valueOf(0)));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { ROTATION });
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(ROTATION)).intValue();
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(ROTATION, Integer.valueOf(meta));
	}

	/**
	 * Called when a neighboring block changes.
	 */
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid()) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}

		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}
}