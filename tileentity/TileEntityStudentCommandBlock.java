package com.dyn.item.tileentity;

import com.dyn.item.blocks.cmdblock.StudentCommandBlockLogic;

import net.minecraft.command.CommandResultStats;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityStudentCommandBlock extends TileEntity {
	private final StudentCommandBlockLogic commandBlockLogic = new StudentCommandBlockLogic() {
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
			return TileEntityStudentCommandBlock.this.getWorld();
		}

		/**
		 * Get the position in the world. <b>{@code null} is not allowed!</b> If
		 * you are not an entity in the world, return the coordinates 0, 0, 0
		 */
		@Override
		public BlockPos getPosition() {
			return TileEntityStudentCommandBlock.this.pos;
		}

		/**
		 * Get the position vector. <b>{@code null} is not allowed!</b> If you
		 * are not an entity in the world, return 0.0D, 0.0D, 0.0D
		 */
		@Override
		public Vec3 getPositionVector() {
			return new Vec3(TileEntityStudentCommandBlock.this.pos.getX() + 0.5D,
					TileEntityStudentCommandBlock.this.pos.getY() + 0.5D,
					TileEntityStudentCommandBlock.this.pos.getZ() + 0.5D);
		}

		/**
		 * Sets the command.
		 */
		@Override
		public void setCommand(String command) {
			System.out.println("TE Setting Command to: " + command);
			super.setCommand(command);
			TileEntityStudentCommandBlock.this.markDirty();
		}

		@Override
		public void updateCommand() {
			System.out.println("TE Updating Command");
			TileEntityStudentCommandBlock.this.getWorld().markBlockForUpdate(TileEntityStudentCommandBlock.this.pos);
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
		return new S35PacketUpdateTileEntity(pos, 2, nbttagcompound);
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