package com.dyn.fixins.blocks.dialog;

import com.dyn.render.RenderMod;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class StudentDialogBlock extends DialogBlock {

	public StudentDialogBlock() {
		super();
		setHardness(.5f);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		// without this it fires twice
		if (worldIn.isRemote) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof DialogBlockTileEntity) {
				RenderMod.proxy.openEditDialogInterface((DialogBlockTileEntity) tileentity);
			} else {
				return false;
			}
		}
		return true;
	}
}
