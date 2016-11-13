package com.dyn.fixins.entity.crash;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public class ModelCrashTestEntity extends ModelBiped {

	public ModelCrashTestEntity() {
		super();
		isChild = false;
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_,
			float p_78088_6_, float scale) {
		super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);

	}
}
