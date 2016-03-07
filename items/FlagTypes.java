package com.dyn.item.items;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.EnumChatFormatting;

public enum FlagTypes {
	YELLOW(3, 0, "yellow", "yellow", MapColor.yellowColor, EnumChatFormatting.YELLOW), BLUE(2, 1, "blue", "blue",
			MapColor.blueColor, EnumChatFormatting.DARK_BLUE), GREEN(1, 2, "green", "green", MapColor.greenColor,
					EnumChatFormatting.DARK_GREEN), RED(0, 3, "red", "red", MapColor.redColor,
							EnumChatFormatting.DARK_RED);
	private static FlagTypes[] META_LOOKUP = new FlagTypes[values().length];
	private static FlagTypes[] FLAG_DMG_LOOKUP = new FlagTypes[values().length];

	static {
		FlagTypes[] var0 = values();
		int var1 = var0.length;

		for (int var2 = 0; var2 < var1; ++var2) {
			FlagTypes var3 = var0[var2];
			META_LOOKUP[var3.getMetadata()] = var3;
			FLAG_DMG_LOOKUP[var3.getFlagDamage()] = var3;
		}
	}

	public static FlagTypes byFlagDamage(int damage) {
		if ((damage < 0) || (damage >= FLAG_DMG_LOOKUP.length)) {
			damage = 0;
		}

		return FLAG_DMG_LOOKUP[damage];
	}

	public static FlagTypes byMetadata(int meta) {
		if ((meta < 0) || (meta >= META_LOOKUP.length)) {
			meta = 0;
		}

		return META_LOOKUP[meta];
	}

	private int meta;
	private int flagDamage;
	private String name;

	private String unlocalizedName;

	private MapColor mapColor;

	private FlagTypes(int meta, int dyeDamage, String name, String unlocalizedName, MapColor mapColorIn,
			EnumChatFormatting chatColor) {
		this.meta = meta;
		this.flagDamage = dyeDamage;
		this.name = name;
		this.unlocalizedName = unlocalizedName;
		this.mapColor = mapColorIn;
	}

	public int getFlagDamage() {
		return this.flagDamage;
	}

	public MapColor getMapColor() {
		return this.mapColor;
	}

	public int getMetadata() {
		return this.meta;
	}

	public String getName() {
		return this.name;
	}

	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}

	@Override
	public String toString() {
		return this.unlocalizedName;
	}
}
