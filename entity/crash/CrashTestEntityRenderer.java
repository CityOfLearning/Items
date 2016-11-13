package com.dyn.fixins.entity.crash;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class CrashTestEntityRenderer implements IRenderFactory {

	@Override
	public Render createRenderFor(RenderManager manager) {
		return new RenderCrashTestEntity(manager, new ModelCrashTestEntity(), 0.3F);
	}

}