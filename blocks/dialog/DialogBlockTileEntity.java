package com.dyn.fixins.blocks.dialog;

import com.dyn.DYNServerMod;
import com.dyn.fixins.entity.crash.CrashTestEntity;
import com.dyn.fixins.entity.ghost.GhostEntity;
import com.dyn.robot.entity.DynRobotEntity;
import com.rabbit.gui.component.display.entity.DisplayEntity;
import com.rabbit.gui.component.display.entity.DisplayEntityHead;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class DialogBlockTileEntity extends TileEntity {

	private BlockPos corner1;
	private BlockPos corner2;
	private String text;

	// Entity Stuff
	private int entityId;
	private String entitySkin = "";
	private EntityLivingBase entity;
	private String entityName = "";

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeToNBT(tagCompound);
		return new S35PacketUpdateTileEntity(pos, getBlockMetadata(), tagCompound);
	}

	public String getText() {
		return text;
	}

	public BlockPos getCorner1() {
		return corner1;
	}

	public BlockPos getCorner2() {
		return corner2;
	}

	public EntityLivingBase getEntity() {
		return entity;
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
		text = compound.getString("tileText");
		corner1 = new BlockPos(compound.getInteger("tileX1"), compound.getInteger("tileY1"),
				compound.getInteger("tileZ1"));
		corner2 = new BlockPos(compound.getInteger("tileX2"), compound.getInteger("tileY2"),
				compound.getInteger("tileZ2"));
		entitySkin = compound.getString("skin");
		if (compound.hasKey("entity")) {
			entityId = compound.getInteger("entityId");
			if (entityId == 90) {
				entity = (EntityLivingBase) EntityList.createEntityByName(compound.getString("entityName"), worldObj);
				entityName = compound.getString("entityName");
				if (entity == null) {
					if (entityName.equals("DisplayHead")) {
						entity = new DisplayEntityHead(worldObj);
					} else if (entityName.equals("DisplayEntity")) {
						entity = new DisplayEntity(worldObj);
					} else if (entityName.equals("DYNRobot")) {
						entity = new DynRobotEntity(worldObj);
					} else if (entityName.equals("Dummy")) {
						entity = new CrashTestEntity(worldObj);
					} else if (entityName.equals("Ghost")) {
						entity = new GhostEntity(worldObj);
					}
				}
			} else {
				entity = (EntityLivingBase) EntityList.createEntityByID(entityId, worldObj);
				entityName = compound.getString("entityName");
			}
			if (entity != null && compound.hasKey("entity")) {
				try {
					entity.readEntityFromNBT(compound.getCompoundTag("entity"));
				} catch (NullPointerException npe) {
					DYNServerMod.logger.warn("Got a null pointer reading entity NBT, likely due to ageable entity");
				}
			}
		}
	}

	public void setEntity(EntityLivingBase entity, int id) {
		this.entityName = entity.getName();
		this.entityId = id;
		this.entity = entity;
	}

	public void setData(String text, int x, int y, int z) {
		setData(text, x / 2, y / 2, z / 2, x / 2, y / 2, z / 2);
	}

	public void setData(String text, BlockPos corner1, BlockPos corner2) {
		this.text = text;
		this.corner1 = corner1;
		this.corner2 = corner2;
	}

	public void setData(String text, int x1, int y1, int z1, int x2, int y2, int z2) {
		setData(text, new BlockPos(x1, y1, z1), new BlockPos(x2, y2, z2));
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("tileText", text);
		compound.setInteger("tileX1", corner1.getX());
		compound.setInteger("tileY1", corner1.getY());
		compound.setInteger("tileZ1", corner1.getZ());
		compound.setInteger("tileX2", corner2.getX());
		compound.setInteger("tileY2", corner2.getY());
		compound.setInteger("tileZ2", corner2.getZ());
		compound.setString("skin", entitySkin);
		if (entity != null) {
			compound.setInteger("entityId", entityId);
			compound.setString("entityName", entityName);
			NBTTagCompound entityTag = new NBTTagCompound();
			try {
				entity.writeEntityToNBT(entityTag);
			} catch (NullPointerException npe) {
				DYNServerMod.logger.warn("Got a null pointer writing entity NBT, likely due to ageable entity");
			}
			compound.setTag("entity", entityTag);
		}
	}
}
