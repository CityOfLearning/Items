package com.dyn.fixins.blocks.redstone.proximity;

import com.dyn.render.RenderMod;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class StudentProximityBlock extends ProximityBlock {

	public StudentProximityBlock() {
		super();
		setHardness(0.5f);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		// without this it fires twice
		if (worldIn.isRemote) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof ProximityBlockTileEntity) {
				RenderMod.proxy.openSetProximityInterface((ProximityBlockTileEntity) tileentity);
			} else {
				return false;
			}
		}
		return true;
	}
}
