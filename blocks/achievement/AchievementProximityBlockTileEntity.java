package com.dyn.fixins.blocks.achievement;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class AchievementProximityBlockTileEntity extends TileEntity {

	private BlockPos corner1;
	private BlockPos corner2;

	private int achievementId;

	public int getAchievementId() {
		return achievementId;
	}

	public BlockPos getCorner1() {
		return corner1;
	}

	public BlockPos getCorner2() {
		return corner2;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeToNBT(tagCompound);
		return new S35PacketUpdateTileEntity(pos, getBlockMetadata(), tagCompound);
	}

	public void markForUpdate() {
		worldObj.markBlockForUpdate(pos);
		markDirty();
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		corner1 = new BlockPos(compound.getInteger("tileX1"), compound.getInteger("tileY1"),
				compound.getInteger("tileZ1"));
		corner2 = new BlockPos(compound.getInteger("tileX2"), compound.getInteger("tileY2"),
				compound.getInteger("tileZ2"));
		achievementId = compound.getInteger("achId");
	}

	public void setAchievementId(int achievementId) {
		this.achievementId = achievementId;
	}

	public void setCorners(BlockPos corner1, BlockPos corner2) {
		this.corner1 = corner1;
		this.corner2 = corner2;
	}

	public void setCorners(int x, int y, int z) {
		setCorners(x / 2, y / 2, z / 2, x / 2, y / 2, z / 2);
	}

	public void setCorners(int x1, int y1, int z1, int x2, int y2, int z2) {
		setCorners(new BlockPos(x1, y1, z1), new BlockPos(x2, y2, z2));
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("tileX1", corner1.getX());
		compound.setInteger("tileY1", corner1.getY());
		compound.setInteger("tileZ1", corner1.getZ());
		compound.setInteger("tileX2", corner2.getX());
		compound.setInteger("tileY2", corner2.getY());
		compound.setInteger("tileZ2", corner2.getZ());
		compound.setInteger("achId", achievementId);
	}
}
