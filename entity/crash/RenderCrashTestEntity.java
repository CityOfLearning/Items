package com.dyn.fixins.entity.crash;

import com.dyn.fixins.reference.Reference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderCrashTestEntity extends RenderLiving {

	public RenderCrashTestEntity(RenderManager rendermanagerIn, ModelCrashTestEntity modelDynRobot, float shadowSize) {
		super(rendermanagerIn, modelDynRobot, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return new ResourceLocation(Reference.MOD_ID, "textures/models/dummy.png");
	}

}