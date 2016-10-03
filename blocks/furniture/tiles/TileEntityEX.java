//

//

package com.dyn.item.blocks.furniture.tiles;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEX extends TileEntity {
	public Map<String, Object> tempData;
	public NBTTagCompound extraData;

	public TileEntityEX() {
		tempData = new HashMap<String, Object>();
		extraData = new NBTTagCompound();
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		extraData = compound.getCompoundTag("ExtraData");
	}

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("ExtraData", extraData);
	}
}
