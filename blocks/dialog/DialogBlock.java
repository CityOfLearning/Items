package com.dyn.fixins.blocks.dialog;

import java.util.List;
import java.util.Random;

import com.dyn.DYNServerMod;
import com.dyn.render.RenderMod;
import com.dyn.utils.PlayerLevel;
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

public class DialogBlock extends Block implements ITileEntityProvider {

	public DialogBlock() {
		super(Material.clay);
		setBlockUnbreakable();
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new DialogBlockTileEntity();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		// without this it fires twice
		if (worldIn.isRemote) {
			if (DYNServerMod.accessLevel == PlayerLevel.ADMIN) {
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof DialogBlockTileEntity) {
					RenderMod.proxy.openEditDialogInterface((DialogBlockTileEntity) tileentity);
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
		if (tileentity instanceof DialogBlockTileEntity) {
			DialogBlockTileEntity tileEntityData = (DialogBlockTileEntity) tileentity;
			tileEntityData.setData("", 10, 4, 10);
			tileEntityData.setEntity(new DisplayEntityHead(worldIn));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (Minecraft.getMinecraft().inGameHasFocus) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof DialogBlockTileEntity) {
				int xradius = ((DialogBlockTileEntity) tileentity).getXRadius();
				int yradius = ((DialogBlockTileEntity) tileentity).getYRadius();
				int zradius = ((DialogBlockTileEntity) tileentity).getZRadius();
				List<EntityPlayer> players = worldIn.getEntitiesWithinAABB(EntityPlayer.class,
						AxisAlignedBB.fromBounds(pos.getX() - (xradius / 2.0f), pos.getY() - (yradius / 2.0f),
								pos.getZ() - (zradius / 2.0f), pos.getX() + (xradius / 2.0f),
								pos.getY() + (yradius / 2.0f), pos.getZ() + (zradius / 2.0f)));

				if (players.contains(Minecraft.getMinecraft().thePlayer)) {
					RenderMod.proxy.toggleDialogHud(((DialogBlockTileEntity) tileentity).getEntity(), true, ((DialogBlockTileEntity) tileentity).getText(), 150);
				}
			}
		}
	}

	@Override
	public int tickRate(World worldIn) {
		return 10;
	}

}
