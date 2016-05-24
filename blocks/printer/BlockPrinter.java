package com.dyn.item.blocks.printer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPrinter extends Block {
	/*
	 * @SideOnly(Side.CLIENT) IIcon[] icons;
	 */

	public BlockPrinter() {
		super(Material.ground);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	/*
	 * public void onBlockAdded(World world, int i, int j, int k) {
	 * super.onBlockAdded(world, i, j, k); setDefaultDirection(world, i, j, k);
	 * }
	 *
	 * private void setDefaultDirection(World world, int i, int j, int k) { if
	 * (world.isRemote) { return; } Block l = world.getBlock(i, j, k - 1); Block
	 * i1 = world.getBlock(i, j, k + 1); Block j1 = world.getBlock(i - 1, j, k);
	 * Block k1 = world.getBlock(i + 1, j, k); byte byte0 = 3; if
	 * ((l.isOpaqueCube()) && (!i1.isOpaqueCube())) { byte0 = 3; } if
	 * ((i1.isOpaqueCube()) && (!l.isOpaqueCube())) { byte0 = 2; } if
	 * ((j1.isOpaqueCube()) && (!k1.isOpaqueCube())) { byte0 = 5; } if
	 * ((k1.isOpaqueCube()) && (!j1.isOpaqueCube())) { byte0 = 4; }
	 * world.setBlockMetadataWithNotify(i, j, k, byte0, 3); }
	 *
	 * public void onBlockPlacedBy(World world, int i, int j, int k,
	 * EntityLivingBase entityliving, ItemStack itemStack) { int l =
	 * MathHelper.floor_double(entityliving.rotationYaw * 4.0F / 360.0F + 0.5D)
	 * & 0x3; if (l == 0) { world.setBlockMetadataWithNotify(i, j, k, 2, 3); }
	 * if (l == 1) { world.setBlockMetadataWithNotify(i, j, k, 5, 3); } if (l ==
	 * 2) { world.setBlockMetadataWithNotify(i, j, k, 3, 3); } if (l == 3) {
	 * world.setBlockMetadataWithNotify(i, j, k, 4, 3); } }
	 *
	 * @SideOnly(Side.CLIENT) public IIcon getIcon(IBlockAccess iblockaccess,
	 * int i, int j, int k, int l) { if (l == 1) { return this.blockIcon; } int
	 * i1 = iblockaccess.getBlockMetadata(i, j, k); if (i1 > 6) { if (l != i1 -
	 * 5) { return this.icons[0]; } return this.icons[2]; } if (l != i1) {
	 * return this.icons[0]; } return this.icons[1]; }
	 *
	 * @SideOnly(Side.CLIENT) public IIcon getIcon(int i, int m) { if (i == 3) {
	 * return this.icons[1]; } if (i == 1) { return this.blockIcon; } return
	 * this.icons[0]; }
	 *
	 * @SideOnly(Side.CLIENT) public void registerBlockIcons(IIconRegister
	 * par1IconRegister) { this.blockIcon =
	 * par1IconRegister.registerIcon("PrinterBlock:printer_up"); this.icons =
	 * new IIcon[3]; this.icons[0] =
	 * par1IconRegister.registerIcon("PrinterBlock:printer_side"); this.icons[1]
	 * = par1IconRegister.registerIcon("PrinterBlock:printer_side2");
	 * this.icons[2] =
	 * par1IconRegister.registerIcon("PrinterBlock:printer_side3"); }
	 *
	 * public void onBlockClicked(World world, int i, int j, int k, EntityPlayer
	 * entityplayer) { int l = world.getBlockMetadata(i, j, k); if (l > 6) {
	 * world.setBlockMetadataWithNotify(i, j, k, l - 5, 3); } else {
	 * world.setBlockMetadataWithNotify(i, j, k, l + 5, 3); }
	 * world.markBlockForUpdate(i, j, k); }
	 *
	 * public boolean onBlockActivated(World world, int i, int j, int k,
	 * EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	 * { if (world.isRemote) { int l = world.getBlockMetadata(i, j, k) < 6 ? 1 :
	 * -1; FMLClientHandler.instance().displayGuiScreen(player, new
	 * GuiPrinter(world, x, y, z, meta, dir)); //
	 * PrinterBlockMod.proxy.openGUI(entityplayer, world, i, j + 1, k,
	 * world.getBlockMetadata(i, j, k), l); } return true; }
	 */
}
