package com.dyn.item.gui;

import com.dyn.item.tileentity.TileEntityStudentCommandBlock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * User: brandon3055 Date: 06/01/2015
 *
 * The container is used to link the client side gui to the server side
 * inventory and it is where you add the slots to your gui. It can also be used
 * to sync server side data with the client but that will be covered in a later
 * tutorial
 */
public class DummyContainer extends Container {

	public DummyContainer(TileEntityStudentCommandBlock tileEntityInventoryBasic) {
	}

	// Vanilla calls this method every tick to make sure the player is still
	// able to access the inventory, and if not closes the gui
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
