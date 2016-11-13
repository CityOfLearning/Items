package com.dyn.fixins.blocks.dialog;

import com.dyn.DYNServerMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DialogBlockTileEntity extends TileEntity {

	private int blockX;
	private int blockY;
	private int blockZ;
	private String text;
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

	public int getXRadius() {
		return blockX;
	}

	public int getYRadius() {
		return blockY;
	}

	public int getZRadius() {
		return blockZ;
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
		blockX = compound.getInteger("tileXRadius");
		blockY = compound.getInteger("tileYRadius");
		blockZ = compound.getInteger("tileZRadius");
		entitySkin = compound.getString("skin");
		if (compound.hasKey("entityName")) {
			entity = (EntityLivingBase) EntityList.createEntityByName(compound.getString("entityName"), worldObj);
			entityName = compound.getString("entityName");
		}
	}

	public void setEntity(EntityLivingBase entity) {
		this.entityName = entity.getName();
		this.entity = entity;
	}

	public void setData(String text, int x, int y, int z) {
		this.text = text;
		blockX = x;
		blockY = y;
		blockZ = z;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("tileText", text);
		compound.setInteger("tileXRadius", blockX);
		compound.setInteger("tileYRadius", blockY);
		compound.setInteger("tileZRadius", blockZ);
		compound.setString("skin", entitySkin);
		if (entity != null) {
			compound.setString("entityName", entityName);
		}
	}
}
