package com.dyn.fixins.blocks.arrow;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockArrowSign2 extends Block {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 7);

	public BlockArrowSign2() {
		super(Material.wood);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ROTATION, 0));
	}

	// Is the neighbouring block in the given direction suitable for mounting
	// the target onto?
	private boolean adjacentBlockIsASuitableSupport(World world, BlockPos thisPos, EnumFacing directionOfNeighbour) {
		BlockPos neighbourPos = thisPos.offset(directionOfNeighbour);
		EnumFacing neighbourSide = directionOfNeighbour.getOpposite();
		boolean DEFAULT_SOLID_VALUE = false;
		return world.isSideSolid(neighbourPos, neighbourSide, DEFAULT_SOLID_VALUE);
	}

	/**
	 * Can we place the block at this location?
	 *
	 * @param worldIn
	 * @param thisBlockPos
	 *            the position of this block (not the neighbour)
	 * @param faceOfNeighbour
	 *            the face of the neighbour that is adjacent to this block. If I
	 *            am facing east, with a stone block to the east of me, and I
	 *            click on the westward-pointing face of the block,
	 *            faceOfNeighbour is WEST
	 * @return true if the block can be placed here
	 */
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos thisBlockPos, EnumFacing faceOfNeighbour) {
		EnumFacing directionOfNeighbour = faceOfNeighbour.getOpposite();
		return adjacentBlockIsASuitableSupport(worldIn, thisBlockPos, directionOfNeighbour);
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, ROTATION });
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		System.out.println(
				"Rotation Value: " + state.getValue(ROTATION).intValue() + " Facing Value: " + state.getValue(FACING));
		return state;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		updateBlockBounds(state);
		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i;

		switch (state.getValue(FACING)) {
		case EAST:
			i = 1;
			break;
		case WEST:
			i = 2;
			break;
		case SOUTH:
			i = 3;
			break;
		case NORTH:
			i = 4;
			break;
		case UP:
		default:
			i = 5;
			break;
		case DOWN:
			i = 0;
		}
		i = i | (state.getValue(ROTATION).intValue() << 3);
		return i;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing;

		switch (meta & 7) {
		case 0:
			enumfacing = EnumFacing.DOWN;
			break;
		case 1:
			enumfacing = EnumFacing.EAST;
			break;
		case 2:
			enumfacing = EnumFacing.WEST;
			break;
		case 3:
			enumfacing = EnumFacing.SOUTH;
			break;
		case 4:
			enumfacing = EnumFacing.NORTH;
			break;
		case 5:
		default:
			enumfacing = EnumFacing.UP;
		}
		return getDefaultState().withProperty(FACING, enumfacing).withProperty(ROTATION,
				Integer.valueOf((meta & 56) >> 3));
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
		EnumFacing directionTargetIsPointing = faceOfNeighbour;
		return getDefaultState().withProperty(FACING, directionTargetIsPointing).withProperty(ROTATION, 0);
	}

	// When a neighbour changes - check if the supporting wall has been
	// demolished
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!worldIn.isRemote) { // server side only
			EnumFacing enumfacing = state.getValue(FACING);
			EnumFacing directionOfNeighbour = enumfacing.getOpposite();
			if (!adjacentBlockIsASuitableSupport(worldIn, pos, directionOfNeighbour)) {
				dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	// The block bounds (used for collision and for outlining the block) depend
	// on which way the block is facing
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		IBlockState blockState = worldIn.getBlockState(pos);
		updateBlockBounds(blockState);
	}

	private void setBlockBoundsInPixels(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		final float PIXEL_WIDTH = 1.0F / 16.0F;
		setBlockBounds(minX * PIXEL_WIDTH, minY * PIXEL_WIDTH, minZ * PIXEL_WIDTH, maxX * PIXEL_WIDTH,
				maxY * PIXEL_WIDTH, maxZ * PIXEL_WIDTH);
	}

	// update the block's bounds based on its new state
	private void updateBlockBounds(IBlockState newState) {
		EnumFacing facing = newState.getValue(FACING);

		switch (facing) {
		case NORTH: {
			setBlockBoundsInPixels(0, 0, 15, 16, 16, 16);
			break;
		}
		case WEST: {
			setBlockBoundsInPixels(15, 0, 0, 16, 16, 16);
			break;
		}
		case EAST: {
			setBlockBoundsInPixels(0, 0, 0, 1, 16, 16);
			break;
		}
		case SOUTH: {
			setBlockBoundsInPixels(0, 0, 0, 16, 16, 1);
			break;
		}
		case UP: {
			setBlockBoundsInPixels(0, 0, 0, 16, 1, 16);
			break;
		}
		case DOWN: {
			setBlockBoundsInPixels(0, 15, 0, 16, 16, 16);
			break;
		}
		}
	}

}
