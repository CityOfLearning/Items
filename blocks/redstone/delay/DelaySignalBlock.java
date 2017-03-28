package com.dyn.fixins.blocks.redstone.delay;

import java.util.Random;

import com.dyn.fixins.blocks.DynBlockManager;
import com.dyn.fixins.items.DynItemManager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DelaySignalBlock extends BlockRedstoneDiode {
	// determines if the delay is how long the signal takes to
	// turn on or turn off
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static final PropertyBool TYPE = PropertyBool.create("buffer");
	public static final PropertyInteger DELAY = PropertyInteger.create("delay", 1, 4);

	public DelaySignalBlock(boolean powered) {
		super(powered);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
				.withProperty(DELAY, Integer.valueOf(1)).withProperty(TYPE, Boolean.valueOf(false))
				.withProperty(POWERED, Boolean.valueOf(false)));
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!playerIn.capabilities.allowEdit) {
			return false;
		} else {
			worldIn.setBlockState(pos, state.cycleProperty(DELAY), 3);
			return true;
		}
	}

	/**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return DynItemManager.delayBlockItem;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos)
    {
        return DynItemManager.delayBlockItem;
    }

	
	protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
		boolean flag = this.shouldBePowered(worldIn, pos, state);

		if ((this.isRepeaterPowered && !flag || !this.isRepeaterPowered && flag)
				&& !worldIn.isBlockTickPending(pos, this)) {
			int i = -1;

			if (this.isFacingTowardsRepeater(worldIn, pos, state)) {
				i = -3;
			} else if (this.isRepeaterPowered) {
				i = -2;
			}

			if (flag) {
				worldIn.updateBlockTick(pos, this, this.isLocked(worldIn, pos, state) ? this.getDelay(state) : 1, i);
			} else {
				// always delay the off update
				worldIn.updateBlockTick(pos, this, this.getDelay(state), i);
			}
		}
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		boolean flag = this.shouldBePowered(worldIn, pos, state);

		if (this.isRepeaterPowered && !flag) {
			worldIn.setBlockState(pos, this.getUnpoweredState(state), 2);
		} else if (!this.isRepeaterPowered) {
			worldIn.setBlockState(pos, this.getPoweredState(state), 2);
		}
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(TYPE, Boolean.valueOf(this.isLocked(worldIn, pos, state))).withProperty(POWERED,
				Boolean.valueOf(getPowerOnSide(worldIn, pos.offset(state.getValue(FACING)), state.getValue(FACING)) > 0));
	}

	public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
		return this.getPowerOnSides(worldIn, pos, state) > 0;
	}

	protected boolean canPowerSide(Block blockIn) {
		return true;
	}

	protected int getDelay(IBlockState state) {
		return ((Integer) state.getValue(DELAY)).intValue() * 20;
	}

	protected IBlockState getPoweredState(IBlockState unpoweredState) {
		Integer integer = (Integer) unpoweredState.getValue(DELAY);
		Boolean obool = (Boolean) unpoweredState.getValue(TYPE);
		Boolean pbool = (Boolean) unpoweredState.getValue(POWERED);
		EnumFacing enumfacing = (EnumFacing) unpoweredState.getValue(FACING);
		return DynBlockManager.delayBlock.getDefaultState().withProperty(FACING, enumfacing)
				.withProperty(DELAY, integer).withProperty(TYPE, obool).withProperty(POWERED, pbool);
	}

	protected IBlockState getUnpoweredState(IBlockState poweredState) {
		Integer integer = (Integer) poweredState.getValue(DELAY);
		Boolean obool = (Boolean) poweredState.getValue(TYPE);
		Boolean pbool = (Boolean) poweredState.getValue(POWERED);
		EnumFacing enumfacing = (EnumFacing) poweredState.getValue(FACING);
		return DynBlockManager.delayBlockOff.getDefaultState().withProperty(FACING, enumfacing)
				.withProperty(DELAY, integer).withProperty(TYPE, obool).withProperty(POWERED, pbool);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (this.isRepeaterPowered) {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			double d0 = (double) ((float) pos.getX() + 0.5F) + (double) (rand.nextFloat() - 0.5F) * 0.2D;
			double d1 = (double) ((float) pos.getY() + 0.4F) + (double) (rand.nextFloat() - 0.5F) * 0.2D;
			double d2 = (double) ((float) pos.getZ() + 0.5F) + (double) (rand.nextFloat() - 0.5F) * 0.2D;
			float f = -5.0F;

			if (rand.nextBoolean()) {
				f = (float) (((Integer) state.getValue(DELAY)).intValue() * 2 - 1);
			}

			f = f / 16.0F;
			double d3 = (double) (f * (float) enumfacing.getFrontOffsetX());
			double d4 = (double) (f * (float) enumfacing.getFrontOffsetZ());
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		this.notifyNeighbors(worldIn, pos, state);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta))
				.withProperty(TYPE, Boolean.valueOf(false)).withProperty(POWERED, Boolean.valueOf(false))
				.withProperty(DELAY, Integer.valueOf(1 + (meta >> 2)));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
		i = i | ((Integer) state.getValue(DELAY)).intValue() - 1 << 2;
		return i;
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, DELAY, TYPE, POWERED });
	}
}