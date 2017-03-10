package com.dyn.fixins.blocks.decision;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.dyn.DYNServerMod;
import com.dyn.fixins.entity.crash.CrashTestEntity;
import com.dyn.fixins.entity.ghost.GhostEntity;
import com.dyn.render.RenderMod;
import com.dyn.robot.entity.DynRobotEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rabbit.gui.component.display.entity.DisplayEntity;
import com.rabbit.gui.component.display.entity.DisplayEntityHead;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class DecisionBlockTileEntity extends TileEntity {

	public static class Choice {

		public static final Choice NONE = new Choice(0, "none");
		public static final Choice REDSTONE = new Choice(1, "none");

		public static Choice parse(String toParse) {
			if (toParse.contains("redstone")) {
				return REDSTONE;
			} else if (toParse.contains("cmd")) {
				return new Choice(2, toParse.split(Pattern.quote("|"))[1]);
			}
			return NONE;
		}

		private String value;

		private int id;

		public Choice(int id, String value) {
			this.id = id;
			this.value = value;
		}

		@Override
		public boolean equals(Object o) {
			if ((o instanceof Choice) && (((Choice) o).getId() == id) && (((Choice) o).getValue() == value)) {
				return true;
			}
			return false;
		}

		public int getId() {
			return id;
		}

		public String getType() {
			if (id == 0) {
				return "NONE";
			} else if (id == 1) {
				return "REDSTONE";
			} else {
				return "COMMAND";
			}
		}

		public String getValue() {
			return value;
		}

		public void setValue(String val) {
			value = val;
		}

		@Override
		public String toString() {
			if (id == 0) {
				return "none";
			} else if (id == 1) {
				return "redstone";
			} else {
				return "cmd|" + value;
			}
		}
	}

	private BlockPos corner1;
	private BlockPos corner2;
	private String text;

	private Map<String, Choice> choices = Maps.newHashMap();
	private List<EntityPlayer> prevDetectedPlayers = Lists.newArrayList();

	// Entity Stuff
	private int entityId;
	private String entitySkin = "";
	private EntityLivingBase entity;
	private String entityName = "";

	public void clearList() {
		prevDetectedPlayers.clear();
	}

	public Map<String, Choice> getChoices() {
		return choices;
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

	public EntityLivingBase getEntity() {
		return entity;
	}

	public String getText() {
		return text;
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

		choices.clear();

		NBTTagList nbttaglist = compound.getTagList("choices", 10);
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound choicetag = nbttaglist.getCompoundTagAt(i);
			choices.put(choicetag.getString("choice"), Choice.parse(choicetag.getString("response")));
		}

		if (compound.hasKey("entity")) {
			entityId = compound.getInteger("entityId");
			if (entityId == 90) {
				entityName = compound.getString("entityName");
				entity = (EntityLiving) EntityList.createEntityByName(entityName, worldObj);
				if (entity == null) {
					if (entityName.equals("DisplayHead")) {
						entity = new DisplayEntityHead(worldObj);
					} else if (entityName.equals("DisplayEntity")) {
						entity = new DisplayEntity(worldObj);
					} else if (entityName.equals("Robot")) {
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
				if (entity == null) {
					entity = (EntityLivingBase) EntityList.createEntityByName(entityName, worldObj);
				}
			}
			if ((entity != null) && compound.hasKey("entity")) {
				try {
					entity.readEntityFromNBT(compound.getCompoundTag("entity"));
				} catch (NullPointerException npe) {
					DYNServerMod.logger.warn("Got a null pointer reading entity NBT, likely due to ageable entity");
				}
			}

		}
	}

	public void setChoices(Map<String, Choice> choice) {
		choices = choice;
	}

	public void setData(String text, BlockPos corner1, BlockPos corner2) {
		this.text = text;
		this.corner1 = corner1;
		this.corner2 = corner2;
	}

	public void setData(String text, int x, int y, int z) {
		setData(text, x / 2, y / 2, z / 2, x / 2, y / 2, z / 2);
	}

	public void setData(String text, int x1, int y1, int z1, int x2, int y2, int z2) {
		setData(text, new BlockPos(x1, y1, z1), new BlockPos(x2, y2, z2));
	}

	public void setEntity(EntityLiving entity, int id) {
		entityName = EntityList.getEntityString(entity);
		if ((entityName == null) || entityName.isEmpty()) {
			if (entity instanceof DisplayEntityHead) {
				entityName = "DisplayHead";
			} else if (entity instanceof DisplayEntity) {
				entityName = "DisplayEntity";
			}
		}
		entityId = id;
		this.entity = entity;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return (oldState.getBlock() != newSate.getBlock());
	}

	public void updatePlayerList(List<EntityPlayer> players) {
		if (players.size() > 0) {
			if (players.size() != prevDetectedPlayers.size()) {
				List<EntityPlayer> allDPlayers = players;
				allDPlayers.removeAll(prevDetectedPlayers);
				for (EntityPlayer player : allDPlayers) {
					if (Minecraft.getMinecraft().thePlayer == player) {
						RenderMod.proxy.openDecisionGui(entity, this);
					}
				}
				prevDetectedPlayers.addAll(players);
			}
		}
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

		NBTTagList nbttaglist = new NBTTagList();
		for (String choice : choices.keySet()) {
			NBTTagCompound choiceTag = new NBTTagCompound();
			choiceTag.setString("choice", choice);
			choiceTag.setString("response", choices.get(choice).toString());
			nbttaglist.appendTag(choiceTag);
		}
		compound.setTag("choices", nbttaglist);

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
