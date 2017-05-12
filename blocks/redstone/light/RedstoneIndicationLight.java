package com.dyn.fixins.blocks.redstone.light;

import java.util.Random;

import com.dyn.fixins.blocks.DynBlockManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RedstoneIndicationLight extends Block {
	private final boolean isOn;

	public RedstoneIndicationLight(boolean isOn) {
		super(Material.redstoneLight);
		this.isOn = isOn;
		setHardness(0.3F);
		setStepSound(soundTypeGlass);
		if (isOn) {
			setLightLevel(1.0F);
		}
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state) {
		return new ItemStack(DynBlockManager.indcationLightOff);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World worldIn, BlockPos pos) {
		return Item.getItemFromBlock(DynBlockManager.indcationLightOff);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(DynBlockManager.indcationLightOff);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			if (isOn && !worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, DynBlockManager.indcationLightOff.getDefaultState(), 2);
			} else if (!isOn && worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, DynBlockManager.indcationLightOn.getDefaultState(), 2);
			}
		}
	}

	/**
	 * Called when a neighboring block changes.
	 */
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!worldIn.isRemote) {
			if (isOn && !worldIn.isBlockPowered(pos)) {
				worldIn.scheduleUpdate(pos, this, 4);
			} else if (!isOn && worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, DynBlockManager.indcationLightOn.getDefaultState(), 2);
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (isOn && !worldIn.isBlockPowered(pos)) {
				worldIn.setBlockState(pos, DynBlockManager.indcationLightOff.getDefaultState(), 2);
			}
		}
	}
}