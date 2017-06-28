package com.dyn.fixins.blocks.achievement;

import java.util.List;
import java.util.Random;

import com.dyn.DYNServerMod;
import com.dyn.utils.PlayerAccessLevel;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class AchievementProximityBlock extends Block implements ITileEntityProvider {

	public AchievementProximityBlock() {
		super(Material.circuits);
		setTickRandomly(true);
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new AchievementProximityBlockTileEntity();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		// without this it fires twice
		if (worldIn.isRemote) {
			if (DYNServerMod.accessLevel == PlayerAccessLevel.ADMIN) {
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof AchievementProximityBlockTileEntity) {
					// RenderMod.proxy.openSetProximityInterface((AchievementProximityBlockTileEntity)
					// tileentity);
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
		if (tileentity instanceof AchievementProximityBlockTileEntity) {
			AchievementProximityBlockTileEntity tileEntityData = (AchievementProximityBlockTileEntity) tileentity;
			tileEntityData.setCorners(10, 4, 10);
			worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof AchievementProximityBlockTileEntity) {
			BlockPos c1 = ((AchievementProximityBlockTileEntity) tileentity).getCorner1();
			BlockPos c2 = ((AchievementProximityBlockTileEntity) tileentity).getCorner2();
			if ((c1 != null) && (c2 != null)) {
				List<EntityPlayer> players = worldIn.getEntitiesWithinAABB(EntityPlayer.class,
						AxisAlignedBB.fromBounds(pos.getX() - c1.getX(), pos.getY() - c1.getY(), pos.getZ() - c1.getZ(),
								pos.getX() + c2.getX(), pos.getY() + c2.getY(), pos.getZ() + c2.getZ()));

				for (EntityPlayer player : players) {

				}
				worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
			}
		}
	}
}
