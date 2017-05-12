package com.dyn.fixins.items;

import java.util.List;
import java.util.Map;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.utils.TreeNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAchievementMedal extends Item {

	private static final Map<String, Integer> RARITY_MAP = Maps.<String, Integer>newHashMap();

	private static void createMappings() {
		List<TreeNode<Achievement>> achTree = Lists.newArrayList();
		for (Achievement ach : AchievementList.achievementList) {
			if (ach.parentAchievement == null) {
				achTree.add(new TreeNode<>(ach));
				continue;
			}
			for (TreeNode<Achievement> root : achTree) {
				if (ach.parentAchievement == root.data) {
					root.addChild(ach);
				} else {
					TreeNode<Achievement> node = root.findTreeNode(treeData -> {
						if (treeData == null) {
							return 1;
						}
						boolean nodeOk = treeData == ach.parentAchievement;
						return nodeOk ? 0 : 1;
					});
					if (node != null) {
						node.addChild(ach);
					}
				}
			}
		}
		for (TreeNode<Achievement> root : achTree) {
			for (TreeNode<Achievement> node : root) {
				if (node.data instanceof AchievementPlus) {
					RARITY_MAP.put(((AchievementPlus) node.data).getName(),
							node.data.getSpecial() ? 0 : Math.min(recursiveTreeSearch(node), 4));
				} else {
					RARITY_MAP.put(node.data.getStatName().getUnformattedText(),
							node.data.getSpecial() ? 0 : Math.min(recursiveTreeSearch(node), 4));
				}
			}
		}
	}

	public static int getRarity(String name) {
		if (RARITY_MAP.size() == 0) {
			createMappings();
		}
		return RARITY_MAP.get(name);
	}

	private static int recursiveTreeSearch(TreeNode<Achievement> node) {
		if (node.isLeaf()) {
			if (node.data.getSpecial()) {
				return 0;
			}
			return 1;
		}
		int max = 0;
		for (TreeNode<Achievement> child : node.children) {
			max = Math.max(max, recursiveTreeSearch(child));
		}
		return max + 1;
	}

	public ItemAchievementMedal() {
		maxStackSize = 1;
		setHasSubtypes(true);
	}

	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (stack.hasTagCompound()) {
			tooltip.add(EnumChatFormatting.GOLD + stack.getTagCompound().getString("ach_name"));
		}
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		List<TreeNode<Achievement>> achTree = Lists.newArrayList();
		for (Achievement ach : AchievementList.achievementList) {
			if (ach.parentAchievement == null) {
				achTree.add(new TreeNode<>(ach));
				continue;
			}
			for (TreeNode<Achievement> root : achTree) {
				if (ach.parentAchievement == root.data) {
					root.addChild(ach);
				} else {
					TreeNode<Achievement> node = root.findTreeNode(treeData -> {
						if (treeData == null) {
							return 1;
						}
						boolean nodeOk = treeData == ach.parentAchievement;
						return nodeOk ? 0 : 1;
					});
					if (node != null) {
						node.addChild(ach);
					}
				}
			}
		}

		for (TreeNode<Achievement> root : achTree) {
			for (TreeNode<Achievement> node : root) {
				ItemStack is = new ItemStack(itemIn);
				NBTTagCompound tag = new NBTTagCompound();
				String name = "";
				if (node.data instanceof AchievementPlus) {
					name = ((AchievementPlus) node.data).getName();
				} else {
					name = node.data.getStatName().getUnformattedText();
				}
				tag.setString("ach_name", name);
				int rarity = node.data.getSpecial() ? 0 : Math.min(recursiveTreeSearch(node), 4);
				tag.setInteger("rarity", rarity);
				is.setTagCompound(tag);
				RARITY_MAP.put(name, rarity);
				is.setItemDamage(rarity);
				subItems.add(is);
			}
		}
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if (worldIn.isRemote) {

		}
		return itemStackIn;
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {

		}
		return true;
	}
}
