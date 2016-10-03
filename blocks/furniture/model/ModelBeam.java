
package com.dyn.item.blocks.furniture.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBeam extends ModelBase {
	ModelRenderer Bar;

	public ModelBeam() {
		(Bar = new ModelRenderer(this, 6, 6)).addBox(0.0f, 0.0f, 0.0f, 5, 5, 12);
		Bar.setRotationPoint(-2.5f, 13.5f, -4.0f);
		Bar.setTextureSize(64, 32);
		Bar.mirror = true;
		setRotation(Bar, 0.0f, 0.0f, 0.0f);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		Bar.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
