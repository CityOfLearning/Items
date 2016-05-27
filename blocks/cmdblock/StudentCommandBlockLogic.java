package com.dyn.item.blocks.cmdblock;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dyn.DYNServerMod;
import com.dyn.item.ItemMod;

import io.netty.buffer.ByteBuf;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class StudentCommandBlockLogic implements ICommandSender {
	/** The formatting for the timestamp on commands run. */
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss");
	/** The number of successful commands run. (used for redstone output) */
	private int successCount;
	private boolean trackOutput = true;
	/** The previously run command. */
	private IChatComponent lastOutput = null;
	/** The command stored in the command block. */
	private String commandStored = "";
	/** The custom name of the command block. (defaults to "@") */
	private String customName = "@";
	private final CommandResultStats resultStats = new CommandResultStats();

	/**
	 * Send a chat message to the CommandSender
	 */
	@Override
	public void addChatMessage(IChatComponent component) {
		if (trackOutput && (getEntityWorld() != null) && !getEntityWorld().isRemote) {
			lastOutput = (new ChatComponentText("[" + timestampFormat.format(new Date()) + "] "))
					.appendSibling(component);
			updateCommand();
		}
	}

	/**
	 * Returns {@code true} if the CommandSender is allowed to execute the
	 * command, {@code false} if not
	 */
	@Override
	public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
		return true;
	}

	/**
	 * Returns the command of the command block.
	 */
	public String getCommand() {
		return commandStored;
	}

	public CommandResultStats getCommandResultStats() {
		return resultStats;
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's
	 * username in chat
	 */
	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getName());
	}

	/**
	 * Returns the this.lastOutput.
	 */
	public IChatComponent getLastOutput() {
		return lastOutput;
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return customName;
	}

	/**
	 * returns the this.successCount int.
	 */
	public int getSuccessCount() {
		return successCount;
	}

	/**
	 * Reads NBT formatting and stored data into variables.
	 */
	public void readDataFromNBT(NBTTagCompound nbt) {
		commandStored = nbt.getString("Command");
		successCount = nbt.getInteger("SuccessCount");

		if (nbt.hasKey("CustomName", 8)) {
			customName = nbt.getString("CustomName");
		}

		if (nbt.hasKey("TrackOutput", 1)) {
			trackOutput = nbt.getBoolean("TrackOutput");
		}

		if (nbt.hasKey("LastOutput", 8) && trackOutput) {
			lastOutput = IChatComponent.Serializer.jsonToComponent(nbt.getString("LastOutput"));
		}

		resultStats.readStatsFromNBT(nbt);
	}

	/**
	 * Returns true if the command sender should be sent feedback about executed
	 * commands
	 */
	@Override
	public boolean sendCommandFeedback() {
		MinecraftServer minecraftserver = MinecraftServer.getServer();
		return (minecraftserver == null) || !minecraftserver.isAnvilFileSet()
				|| minecraftserver.worldServers[0].getGameRules().getBoolean("commandBlockOutput");
	}

	/**
	 * Sets the command.
	 */
	public void setCommand(String command) {
		commandStored = command;
		successCount = 0;
	}

	@Override
	public void setCommandStat(CommandResultStats.Type type, int amount) {
		resultStats.func_179672_a(this, type, amount);
	}

	public void setLastOutput(IChatComponent lastOutputMessage) {
		lastOutput = lastOutputMessage;
	}

	public void setName(String name) {
		customName = name;
	}

	public void setTrackOutput(boolean shouldTrackOutput) {
		trackOutput = shouldTrackOutput;
	}

	public boolean shouldTrackOutput() {
		return trackOutput;
	}

	public void trigger(World worldIn) {
		if (!commandStored.isEmpty()) {
			if (worldIn.isRemote) {
				successCount = 0;
			}

			MinecraftServer minecraftserver = MinecraftServer.getServer();

			if ((minecraftserver != null) && minecraftserver.isAnvilFileSet()
					&& minecraftserver.isCommandBlockEnabled()) {
				ICommandManager icommandmanager = minecraftserver.getCommandManager();

				try {
					lastOutput = null;
					successCount = icommandmanager.executeCommand(this, commandStored);
				} catch (Throwable throwable) {
					CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Executing command block");
					CrashReportCategory crashreportcategory = crashreport.makeCategory("Command to be executed");
					crashreportcategory.addCrashSectionCallable("Command",
							() -> StudentCommandBlockLogic.this.getCommand());
					crashreportcategory.addCrashSectionCallable("Name", () -> StudentCommandBlockLogic.this.getName());
					throw new ReportedException(crashreport);
				}
			} else {
				successCount = 0;
			}
		} else {
			DYNServerMod.logger.info("Command is empty");
		}
	}

	public boolean tryOpenEditCommandBlock(EntityPlayer playerIn) {

		if (playerIn.getEntityWorld().isRemote) {
			ItemMod.proxy.openStudentCommandGui(this);
		}
		return true;
	}

	public abstract void updateCommand();

	/**
	 * Stores data to NBT format.
	 */
	public void writeDataToNBT(NBTTagCompound tagCompound) {
		tagCompound.setString("Command", commandStored);
		tagCompound.setInteger("SuccessCount", successCount);
		tagCompound.setString("CustomName", customName);
		tagCompound.setBoolean("TrackOutput", trackOutput);

		if ((lastOutput != null) && trackOutput) {
			tagCompound.setString("LastOutput", IChatComponent.Serializer.componentToJson(lastOutput));
		}

		resultStats.writeStatsToNBT(tagCompound);
	}
}