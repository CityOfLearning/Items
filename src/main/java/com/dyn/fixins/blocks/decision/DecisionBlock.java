package com.dyn.fixins.blocks.decision;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.dyn.fixins.DYNFixinsMod;
import com.dyn.fixins.blocks.decision.DecisionBlockTileEntity.Choice;
import com.dyn.fixins.util.PlayerAccessLevel;
import com.google.common.collect.Maps;
import com.rabbit.gui.component.display.entity.DisplayEntityHead;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DecisionBlock extends Block implements ITileEntityProvider {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public DecisionBlock() {
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
		return new DecisionBlockTileEntity();
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
				if (tileentity instanceof DecisionBlockTileEntity) {
					DYNFixinsMod.proxy.openEditDecisionBlock((DecisionBlockTileEntity) tileentity);
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
		if (tileentity instanceof DecisionBlockTileEntity) {
			DecisionBlockTileEntity tileEntityData = (DecisionBlockTileEntity) tileentity;
			tileEntityData.setData("", 10, 4, 10);
			tileEntityData.setEntity(new DisplayEntityHead(worldIn), 90);
			Map<String, Choice> choice = Maps.newHashMap();
			choice.put("Choice A", Choice.NONE);
			choice.put("Choice B", Choice.NONE);
			choice.put("Redstone", Choice.REDSTONE);
			choice.put("Command", new Choice(2, "/say hello @p", true));
			tileEntityData.setChoices(choice);
			tileEntityData.setActive(false);
			tileEntityData.setIsQuiz(false);
		}
	}

	// we dont need to update a list since its specific to each client
	// anyways...
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (Minecraft.getMinecraft().inGameHasFocus) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if ((tileentity instanceof DecisionBlockTileEntity) && (((DecisionBlockTileEntity) tileentity).isActive()
					|| (((DecisionBlockTileEntity) tileentity).isRetriggerable()))) {
				BlockPos c1 = ((DecisionBlockTileEntity) tileentity).getCorner1();
				BlockPos c2 = ((DecisionBlockTileEntity) tileentity).getCorner2();
				if ((c1 != null) && (c2 != null)) {
					List<EntityPlayer> players = worldIn.getEntitiesWithinAABB(EntityPlayer.class,
							AxisAlignedBB.fromBounds(pos.getX() - c1.getX(), pos.getY() - c1.getY(),
									pos.getZ() - c1.getZ(), pos.getX() + c2.getX(), pos.getY() + c2.getY(),
									pos.getZ() + c2.getZ()));
					if (!((DecisionBlockTileEntity) tileentity).isActive()
							&& ((DecisionBlockTileEntity) tileentity).isRetriggerable()
							&& !players.contains(Minecraft.getMinecraft().thePlayer)) {
						((DecisionBlockTileEntity) tileentity).setActive(true);
					} else if (((DecisionBlockTileEntity) tileentity).isActive()
							&& players.contains(Minecraft.getMinecraft().thePlayer)) {
						DYNFixinsMod.proxy.openDecisionGui(((DecisionBlockTileEntity) tileentity).getEntity(),
								(DecisionBlockTileEntity) tileentity);
					}
				}
			}
		}
	}

	@Override
	public int tickRate(World worldIn) {
		return 10;
	}

}
