package com.dyn.chunkloader.block;

import com.dyn.chunkloader.TileChunkLoader;
import com.dyn.server.ServerMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockChunkLoader extends BlockContainer {

	boolean placable = false;

	public BlockChunkLoader() {
		super(Material.cloth);
		this.setBlockUnbreakable();
		this.setBlockName("chunkloader");
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block p1, int p2) {
		super.breakBlock(world, i, j, k, p1, p2);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new TileChunkLoader();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return null;
	}
	
	/**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    public int getMobilityFlag()
    {
        return 2;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("dyn:chunkloader");
	}
}

///give Player315 chunkloader:chunkloader 1