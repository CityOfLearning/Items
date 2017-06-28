package com.dyn.fixins.blocks.arrow;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockArrowSign extends Block {

	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 7);

	public BlockArrowSign() {
		super(Material.wood);
		setDefaultState(blockState.getBaseState().withProperty(ROTATION, 0));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { ROTATION });
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ROTATION).intValue();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		System.out.println(meta);
		return getDefaultState().withProperty(ROTATION, meta);
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			System.out.println("Cycling Property");
			worldIn.setBlockState(pos, state.cycleProperty(ROTATION), 3);
			// worldIn.markBlockRangeForRenderUpdate(pos, pos);
			// worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
		}
		return true;
	}

	// Create the appropriate state for the block being placed - in this case,
	// figure out which way the target is facing
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos thisBlockPos, EnumFacing faceOfNeighbour, float hitX,
			float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(ROTATION, 0);
	}
}
