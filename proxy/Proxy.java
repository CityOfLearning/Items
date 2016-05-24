package com.dyn.item.proxy;

import com.dyn.item.blocks.cmdblock.StudentCommandBlockLogic;

public interface Proxy {
	public void init();

	public void openStudentCommandGui(StudentCommandBlockLogic cmdBlockLogic);

	public void renderGUI();
}