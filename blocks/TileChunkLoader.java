package com.dyn.item.blocks;

import java.util.LinkedList;
import java.util.List;

import com.dyn.item.ItemMod;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;

public class TileChunkLoader extends TileEntity {
	private ForgeChunkManager.Ticket chunkTicket;

	public void forceChunkLoading(ForgeChunkManager.Ticket ticket) {
		this.stopChunkLoading();
		this.chunkTicket = ticket;
		for (ChunkCoordIntPair coord : this.getLoadArea()) {
			ItemMod.logger.info(String.format("Force loading chunk %s in %s",
					new Object[] { coord, this.worldObj.provider.getClass() }), new Object[0]);
			ForgeChunkManager.forceChunk(this.chunkTicket, coord);
		}
	}

	public List<ChunkCoordIntPair> getLoadArea() {
		List<ChunkCoordIntPair> loadArea = new LinkedList<ChunkCoordIntPair>();
		ChunkCoordIntPair chunkCoords = new ChunkCoordIntPair((this.getPos().getX() >> 4), (this.getPos().getZ() >> 4));

		loadArea.add(chunkCoords);
		return loadArea;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		this.stopChunkLoading();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
	}

	public void stopChunkLoading() {
		if (this.chunkTicket != null) {
			ForgeChunkManager.releaseTicket(this.chunkTicket);
			this.chunkTicket = null;
		}
	}

	public void unforceChunkLoading() {
		for (Object obj : this.chunkTicket.getChunkList()) {
			ChunkCoordIntPair coord = (ChunkCoordIntPair) obj;
			ForgeChunkManager.unforceChunk(this.chunkTicket, coord);
		}
	}

	@Override
	public void validate() {
		super.validate();
		if ((!this.worldObj.isRemote) && (this.chunkTicket == null)) {
			ForgeChunkManager.Ticket ticket = ForgeChunkManager.requestTicket(ItemMod.instance, this.worldObj,
					ForgeChunkManager.Type.NORMAL);
			if (ticket != null) {
				this.forceChunkLoading(ticket);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
	}
}
