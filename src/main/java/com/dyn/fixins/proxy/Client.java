package com.dyn.fixins.proxy;

import java.util.ArrayList;
import java.util.List;

import com.dyn.fixins.blocks.cmdblock.StudentCommandBlockLogic;
import com.dyn.fixins.blocks.decision.DecisionBlockTileEntity;
import com.dyn.fixins.blocks.dialog.DialogBlockTileEntity;
import com.dyn.fixins.blocks.redstone.proximity.ProximityBlockTileEntity;
import com.dyn.fixins.blocks.redstone.timer.TimerBlockTileEntity;
import com.dyn.fixins.gui.command.StudentComamndGui;
import com.dyn.fixins.gui.decision.EditDecisionBlock;
import com.dyn.fixins.gui.dialog.EditDialogBlock;
import com.dyn.fixins.gui.redstone.SetProximityBlock;
import com.dyn.fixins.gui.redstone.SetTimerBlock;
import com.dyn.fixins.hud.DecisionHud;
import com.dyn.fixins.hud.DialogHud;
import com.dyn.fixins.reference.Reference;
import com.rabbit.gui.RabbitGui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Client implements Proxy {

	private int dialogDuration;
	private DialogHud dialog;
	private boolean showDialog;
	private boolean hasShown;

	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic) {
		Minecraft.getMinecraft().displayGuiScreen(new StudentComamndGui(cmdBlockLogic));
	}

	@Override
	public void preInit() {
	}
	
	@Override
	public void addScheduledTask(Runnable runnable) {
		Minecraft.getMinecraft().addScheduledTask(runnable);
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		// Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
		// your packets will not work as expected because you will be getting a
		// client player even when you are on the server!
		// Sounds absurd, but it's true.

		// Solution is to double-check side before returning the player:
		return ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : ctx.getServerHandler().playerEntity;
	}

	@Override
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		// this causes null pointers in single player...
		return Minecraft.getMinecraft();
	}

	@Override
	public void registerBlockItem(Block block) {
		String blockName = block.getUnlocalizedName().replace("tile.", "");
		Item blockItem = GameRegistry.findItem(Reference.MOD_ID, blockName);
		ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Reference.MOD_ID + ":" + blockName,
				"inventory");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(blockItem, 0, itemModelResourceLocation);
	}

	@Override
	public void registerItem(Item item, String name) {
		GameRegistry.registerItem(item, name);
		item.setUnlocalizedName(name);
		List<ItemStack> list = new ArrayList<>();
		item.getSubItems(item, null, list);
		for (ItemStack stack : list) {
			registerItemModels(item, item.getUnlocalizedName(stack), stack.getItemDamage());
		}
	}

	@Override
	public void registerItemModels(Item item, String name, int meta) {
		if (name.contains("item.")) {
			name = name.replace("item.", "");
		}
		ModelResourceLocation location = new ModelResourceLocation(Reference.MOD_ID + ":" + name, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, location);
	}
	
	@Override
	public void openDecisionGui(EntityLivingBase entity, DecisionBlockTileEntity decisionBlockTileEntity) {
		RabbitGui.proxy.display(new DecisionHud(entity, decisionBlockTileEntity));
	}

	@Override
	public void openEditDecisionBlock(DecisionBlockTileEntity decisionBlock) {
		RabbitGui.proxy.display(new EditDecisionBlock(decisionBlock));
	}

	@Override
	public void openEditDialogInterface(DialogBlockTileEntity block) {
		RabbitGui.proxy.display(new EditDialogBlock(block));
	}

	@Override
	public void openSetProximityInterface(ProximityBlockTileEntity block) {
		RabbitGui.proxy.display(new SetProximityBlock(block));
	}

	@Override
	public void openSetTimerInterface(TimerBlockTileEntity block) {
		RabbitGui.proxy.display(new SetTimerBlock(block));
	}
	
	@Override
	public void toggleDialogHud(EntityLivingBase entity, boolean state, String text, int duration, boolean interupt) {
		if (interupt) {
			if (entity == null) {
				RabbitGui.proxy.display(new DialogHud(text, true));
			} else {
				RabbitGui.proxy.display(new DialogHud(entity, text, true));
			}
		} else {
			showDialog = state;
			if (state) {
				if (!hasShown) {
					if (entity == null) {
						RabbitGui.proxy.display(dialog = new DialogHud());
					} else {
						RabbitGui.proxy.display(dialog = new DialogHud(entity, false));
					}
					RabbitGui.proxy.getCurrentStage().close();
					hasShown = true;
				}
				dialog.setRenderText(text);
				dialog.setEntity(entity);
				dialogDuration = duration;
			}
		}

	}
	
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (Minecraft.getMinecraft().inGameHasFocus) {
			if (showDialog) {
				if (dialogDuration <= 0) {
					dialogDuration = 0;
					showDialog = false;
				} else {
					dialog.onUpdate();
					dialog.onDraw(0, 0, event.renderTickTime);
					dialogDuration--;
				}
			}
		}
	}
}