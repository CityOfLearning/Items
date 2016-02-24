package com.dyn.item.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Flags extends Item {
	public static final String[] types = new String[] { "red", "green", "blue", "yellow" };
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public Flags() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName("dyn:flags");
		this.setTextureName("dyn:ctf_flags");
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		int i = MathHelper.clamp_int(itemStack.getItemDamage(), 0, this.types.length-1);
		return super.getUnlocalizedName() + "." + this.types[i];
	}

	/**
	 * Gets an icon index based on an item's damage value
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int dmg) {
			int j = MathHelper.clamp_int(dmg, 0, this.types.length-1);
			return this.icons[j];
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		int i = MathHelper.clamp_int(itemStack.getItemDamage(), 0, this.types.length-1);
		return WordUtils.capitalize(this.types[i] + " flag");
	}

	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i1, int i2, int i3, int i4,
			float f1, float f2, float f3) {
		return false;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		return itemStack;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return false;
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List itemList) {
		for (int i = 0; i < this.types.length; ++i) {
			itemList.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegistry) {
		this.icons = new IIcon[this.types.length];

		for (int i = 0; i < this.types.length; ++i) {			
			this.icons[i] = iconRegistry.registerIcon(this.getIconString() + "_" + this.types[i]);
		}
	}
}
