package com.dyn.fixins.blocks.redstone.timer;

import java.util.Random;

import com.dyn.DYNServerMod;
import com.dyn.render.RenderMod;
import com.dyn.utils.PlayerLevel;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TimerBlock extends Block implements ITileEntityProvider {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public TimerBlock() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(POWERED, Boolean.valueOf(false)));
		setBlockUnbreakable();
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getValue(POWERED).booleanValue()) {
			notifyNeighbors(worldIn, pos);
		}

		super.breakBlock(worldIn, pos, state);
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
		return new BlockState(this, new IProperty[] { POWERED });
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TimerBlockTileEntity();
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (state.getValue(POWERED).booleanValue()) {
			i |= 8;
		}

		return i;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(POWERED, Boolean.valueOf((meta & 8) > 0));
	}

	@Override
	public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return state.getValue(POWERED).booleanValue() ? 15 : 0;
	}

	@Override
	public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return state.getValue(POWERED).booleanValue() ? 15 : 0;
	}

	private void notifyNeighbors(World worldIn, BlockPos pos) {
		worldIn.notifyNeighborsOfStateChange(pos, this);
		worldIn.notifyNeighborsOfStateChange(pos.up(), this);
		worldIn.notifyNeighborsOfStateChange(pos.down(), this);
		worldIn.notifyNeighborsOfStateChange(pos.east(), this);
		worldIn.notifyNeighborsOfStateChange(pos.west(), this);
		worldIn.notifyNeighborsOfStateChange(pos.north(), this);
		worldIn.notifyNeighborsOfStateChange(pos.south(), this);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		// without this it fires twice
		if (worldIn.isRemote) {
			if (DYNServerMod.accessLevel == PlayerLevel.ADMIN) {
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof TimerBlockTileEntity) {
					RenderMod.proxy.openSetTimerInterface((TimerBlockTileEntity) tileentity);
				} else {
					return false;
				}
			}
			return true;
		}
		return true;
	}

	// Called just after the player places a block.
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TimerBlockTileEntity) {
			TimerBlockTileEntity tileEntityData = (TimerBlockTileEntity) tileentity;
			tileEntityData.setTimerTime(50);
			worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TimerBlockTileEntity) {
			if (!state.getValue(POWERED).booleanValue()) {
				// emit redstone signal
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)), 3);
				notifyNeighbors(worldIn, pos);
				// vanilla tick rate
				worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
			} else {
				worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)), 3);
				notifyNeighbors(worldIn, pos);
				worldIn.scheduleUpdate(pos, this, ((TimerBlockTileEntity) tileentity).getTimer());
			}
		}
	}

}
