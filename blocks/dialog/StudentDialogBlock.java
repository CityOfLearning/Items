package com.dyn.fixins.blocks.dialog;

import java.util.List;
import java.util.Random;

import com.dyn.render.RenderMod;
import com.google.common.collect.Lists;
import com.rabbit.gui.component.display.entity.DisplayEntityHead;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
