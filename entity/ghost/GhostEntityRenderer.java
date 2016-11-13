package com.dyn.fixins.entity.ghost;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class GhostEntityRenderer implements IRenderFactory {

	@Override
	public Render createRenderFor(RenderManager manager) {
		return new RenderGhostEntity(manager, new ModelGhost(), 0.0F, 0.65F);
	}

}