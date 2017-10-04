package com.dyn.fixins.blocks.redstone.proximity;

import java.util.List;
import java.util.Random;

import com.dyn.fixins.DYNFixinsMod;
import com.dyn.fixins.util.PlayerAccessLevel;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ProximityBlock extends Block implements ITileEntityProvider {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public ProximityBlock() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(POWERED, Boolean.valueOf(false)));
		setBlockUnbreakable();
		setTickRandomly(true);
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
		return new ProximityBlockTileEntity();
	}

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

	@Override
	public boolean isFullCube() {
		return false;
	}

	// used by the renderer to control lighting and visibility of other blocks.
	// set to true because this block is opaque and occupies the entire 1x1x1
	// space
	// not strictly required because the default (super method) is true
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public void notifyNeighbors(World worldIn, BlockPos pos) {
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
			if (DYNFixinsMod.accessLevel == PlayerAccessLevel.ADMIN) {
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof ProximityBlockTileEntity) {
					DYNFixinsMod.proxy.openSetProximityInterface((ProximityBlockTileEntity) tileentity);
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
		if (tileentity instanceof ProximityBlockTileEntity) {
			ProximityBlockTileEntity tileEntityData = (ProximityBlockTileEntity) tileentity;
			tileEntityData.setCorners(10, 4, 10);
//			tileEntityData.setValidMob(EnumMobType.PLAYER);
			worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(POWERED)) {
			double d0 = pos.getX() + 0.4F + (rand.nextFloat() * 0.2D);
			double d1 = pos.getY() + 0.6F + (rand.nextFloat() * 0.3D);
			double d2 = pos.getZ() + 0.4F + (rand.nextFloat() * 0.2D);
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof ProximityBlockTileEntity) {
			BlockPos c1 = ((ProximityBlockTileEntity) tileentity).getCorner1();
			BlockPos c2 = ((ProximityBlockTileEntity) tileentity).getCorner2();
			if ((c1 != null) && (c2 != null)) {
				List<EntityLivingBase> detectedEntities = worldIn.getEntitiesWithinAABB(EntityLivingBase.class,
						AxisAlignedBB.fromBounds(pos.getX() - c1.getX(), pos.getY() - c1.getY(), pos.getZ() - c1.getZ(),
								pos.getX() + c2.getX(), pos.getY() + c2.getY(), pos.getZ() + c2.getZ()));

				List<EntityLivingBase> validEntities = Lists.newArrayList();
				for (EntityLivingBase entity : detectedEntities) {
					if (((ProximityBlockTileEntity) tileentity).isValidMobType(entity)) {
						validEntities.add(entity);
					}
				}
				((ProximityBlockTileEntity) tileentity).updateProximityList(validEntities, state, worldIn, this);
				worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
			}
		}
	}

}
