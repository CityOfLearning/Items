package com.dyn.item.blocks;

import java.util.List;
import java.util.Random;

import com.dyn.item.ItemMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class FlagBlock extends Block {

	public static final String[] types = new String[] { "red", "green", "blue", "yellow" };
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public FlagBlock() {
		super(Material.cloth);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHardness(0.1F);
		this.setResistance(6000000.0F);
		this.setStepSound(soundTypeCloth);
		this.setBlockName("ctfBlock");
		this.setBlockTextureName("dyn:ctf_flags");
	}
    

    /**
     * Gets the block's texture. Args: side, meta
     */
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	int i = MathHelper.clamp_int(meta, 0, types.length-1);
        return this.icons[i];
    }

    /**
     * Returns the item to drop on block destruction.
     */
    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
    	System.out.println("Metadata: " + metadata);
        return new ItemStack(ItemMod.flags, 1, metadata).getItem();
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
	public int quantityDropped(Random random)
    {
        return 1;
    }
    
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
	public int damageDropped(int damage)
    {
    	System.out.println("Block Damage amt: " + damage);
        return damage;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
        list.add(new ItemStack(item, 1, 3));
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.icons = new IIcon[types.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon(this.getTextureName() + "_" + types[i]);
        }
    }
}
