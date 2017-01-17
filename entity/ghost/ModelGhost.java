package com.dyn.fixins.entity.ghost;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelGhost extends ModelBase {
	ModelRenderer HeadAndBody;
	ModelRenderer LArm;
	ModelRenderer RArm;

	public ModelGhost() {
		textureWidth = 64;
		textureHeight = 64;

		HeadAndBody = new ModelRenderer(this, 0, 0);
		HeadAndBody.addBox(-3.0F, 0.0F, -3.0F, 6, 21, 6);
		HeadAndBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		HeadAndBody.setTextureSize(64, 64);
		HeadAndBody.mirror = true;
		setRotation(HeadAndBody, 0.0F, 0.0F, 0.0F);
		LArm = new ModelRenderer(this, 34, 0);
		LArm.addBox(-1.0F, -1.0F, -1.0F, 2, 11, 2);
		LArm.setRotationPoint(3.0F, 6.0F, 0.0F);
		LArm.setTextureSize(64, 64);
		LArm.mirror = true;
		setRotation(LArm, 0.0F, 0.0F, -0.3316126F);
		RArm = new ModelRenderer(this, 25, 0);
		RArm.addBox(-1.0F, -1.0F, -1.0F, 2, 11, 2);
		RArm.setRotationPoint(-3.0F, 6.0F, 0.0F);
		RArm.setTextureSize(64, 64);
		RArm.mirror = true;
		setRotation(RArm, 0.0F, 0.0F, 0.3316126F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		LArm.rotateAngleZ = (-0.33F + (MathHelper.cos(f2 * 0.3F) * 3.1415927F * 0.05F));
		RArm.rotateAngleZ = (0.33F + (MathHelper.cos(f2 * 0.32F) * 3.1415927F * 0.05F));
		LArm.rotateAngleX = (-0.33F + (MathHelper.cos(f2 * 0.34F) * 3.1415927F * 0.05F));
		RArm.rotateAngleX = (0.33F + (MathHelper.cos(f2 * 0.36F) * 3.1415927F * 0.05F));

		GlStateManager.pushMatrix();
		{
			GlStateManager.enableBlend();
			GlStateManager.enableNormalize();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(1, 1, 1, ((GhostEntity) entity).getAlpha());
			GlStateManager.translate(0, -1, 0);
			GlStateManager.scale(1.5, 1.5, 1.5);

			HeadAndBody.render(f5);
			LArm.render(f5);
			RArm.render(f5);

			GlStateManager.disableBlend();
		}
		GlStateManager.popMatrix();
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6,
			Entity par7Entity) {
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}
}
