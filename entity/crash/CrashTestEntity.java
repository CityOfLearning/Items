package com.dyn.fixins.entity.crash;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.world.World;

public class CrashTestEntity extends EntityLiving {

	public CrashTestEntity(World worldIn) {
		super(worldIn);
		enablePersistence();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000000.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(10000.0D);
	}

	@Override
	public boolean getCanSpawnHere() {
		return false;
	}

	@Override
	public String getName() {
		return "Dummy";
	}

	@Override
	public double getYOffset() {
		return -0.35D;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ridingEntity == null) {
			List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
					getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

			if ((list != null) && !list.isEmpty()) {
				for (int k2 = 0; k2 < list.size(); ++k2) {
					Entity entity = list.get(k2);

					if ((entity != riddenByEntity) && ((entity instanceof EntityBoat) || (entity instanceof EntityHorse)
							|| (entity instanceof EntityPig))) {
						mountEntity(entity);
						break;
					}
				}
			}
		}
	}
}
