package com.dyn.fixins.entity.ghost;

import com.dyn.fixins.reference.Reference;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderGhostEntity extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, "textures/models/ghost.png");
	protected ModelGhost model;
	private float scale = 1.0F;

	public RenderGhostEntity(RenderManager renderer, ModelGhost model, float par2, float par3) {
		super(renderer, model, par2 * par3);
		this.model = (this.model);
		scale = par3;
	}

	public void func_76986_a(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		renderGhostEntity((GhostEntity) par1Entity, par2, par4, par6, par8, par9);
	}

	public void func_76986_a(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8,
			float par9) {
		renderGhostEntity((GhostEntity) par1EntityLiving, par2, par4, par6, par8, par9);
	}

	protected void func_77041_b(EntityLivingBase par1EntityLiving, float par2) {
		preRenderScale((GhostEntity) par1EntityLiving, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return texture;
	}

	protected void preRenderScale(GhostEntity par1Entity, float par2) {
		GlStateManager.scale(scale, scale, scale);
	}

	public void renderGhostEntity(GhostEntity par1GhostEntity, double par2, double par4, double par6, float par8,
			float par9) {
		super.doRender(par1GhostEntity, par2, par4, par6, par8, par9);
	}

}