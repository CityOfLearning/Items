package com.dyn.fixins.blocks.cmdblock.tileentity;

import com.dyn.fixins.blocks.cmdblock.StudentCommandBlockLogic;

import io.netty.buffer.ByteBuf;
import net.minecraft.command.CommandResultStats;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StudentCommandBlockTileEntity extends TileEntity {
	private final StudentCommandBlockLogic commandBlockLogic = new StudentCommandBlockLogic() {

		@Override
		@SideOnly(Side.CLIENT)
		public int func_145751_f() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void func_145757_a(ByteBuf p_145757_1_) {
			// TODO Auto-generated method stub
			p_145757_1_.writeInt(StudentCommandBlockTileEntity.this.pos.getX());
			p_145757_1_.writeInt(StudentCommandBlockTileEntity.this.pos.getY());
			p_145757_1_.writeInt(StudentCommandBlockTileEntity.this.pos.getZ());
		}

		/**
		 * Returns the entity associated with the command sender. MAY BE NULL!
		 */
		@Override
		public Entity getCommandSenderEntity() {
			return null;
		}

		/**
		 * Get the world, if available. <b>{@code null} is not allowed!</b> If
		 * you are not an entity in the world, return the overworld
		 */
		@Override
		public World getEntityWorld() {
			return StudentCommandBlockTileEntity.this.getWorld();
		}

		/**
		 * Get the position in the world. <b>{@code null} is not allowed!</b> If
		 * you are not an entity in the world, return the coordinates 0, 0, 0
		 */
		@Override
		public BlockPos getPosition() {
			return StudentCommandBlockTileEntity.this.pos;
		}

		/**
		 * Get the position vector. <b>{@code null} is not allowed!</b> If you
		 * are not an entity in the world, return 0.0D, 0.0D, 0.0D
		 */
		@Override
		public Vec3 getPositionVector() {
			return new Vec3(StudentCommandBlockTileEntity.this.pos.getX() + 0.5D,
					StudentCommandBlockTileEntity.this.pos.getY() + 0.5D,
					StudentCommandBlockTileEntity.this.pos.getZ() + 0.5D);
		}

		/**
		 * Sets the command.
		 */
		@Override
		public void setCommand(String command) {
			super.setCommand(command);
			StudentCommandBlockTileEntity.this.markDirty();
		}

		@Override
		public void updateCommand() {
			StudentCommandBlockTileEntity.this.getWorld().markBlockForUpdate(StudentCommandBlockTileEntity.this.pos);
		}
	};

	@Override
	public boolean func_183000_F() {
		return true;
	}

	public StudentCommandBlockLogic getCommandBlockLogic() {
		return commandBlockLogic;
	}

	public CommandResultStats getCommandResultStats() {
		return commandBlockLogic.getCommandResultStats();
	}

	/**
	 * Allows for a specialized description packet to be created. This is often
	 * used to sync tile entity data from the server to the client easily. For
	 * example this is used by signs to synchronise the text to be displayed.
	 */
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		// PacketDispatcher.sendToAll(new SyncStudentCommandMessage(this.pos,
		// nbttagcompound));
		return new S35PacketUpdateTileEntity(pos, 2, nbttagcompound);
	}

	/**
	 * Called when you receive a TileEntityData packet for the location this
	 * TileEntity is currently in. On the client, the NetworkManager will always
	 * be the remote server. On the server, it will be whomever is responsible
	 * for sending the packet.
	 *
	 * @param net
	 *            The NetworkManager the packet originated from
	 * @param pkt
	 *            The data packet
	 */
	@Override
	public void onDataPacket(net.minecraft.network.NetworkManager net,
			net.minecraft.network.play.server.S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		commandBlockLogic.readDataFromNBT(compound);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		commandBlockLogic.writeDataToNBT(compound);
	}
}