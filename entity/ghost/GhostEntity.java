package com.dyn.fixins.entity.ghost;

import java.util.Random;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GhostEntity extends EntityFlying {

	static class AILookAround extends EntityAIBase {
		private GhostEntity parentEntity;

		public AILookAround(GhostEntity ghost) {
			parentEntity = ghost;
			setMutexBits(2);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			return true;
		}

		/**
		 * Updates the task
		 */
		@Override
		public void updateTask() {
			if (parentEntity.getAttackTarget() == null) {
				parentEntity.renderYawOffset = parentEntity.rotationYaw = (-((float) MathHelper
						.atan2(parentEntity.motionX, parentEntity.motionZ)) * 180.0F) / (float) Math.PI;
			} else {
				EntityLivingBase entitylivingbase = parentEntity.getAttackTarget();
				double d0 = 64.0D;

				if (entitylivingbase.getDistanceSqToEntity(parentEntity) < (d0 * d0)) {
					double d1 = entitylivingbase.posX - parentEntity.posX;
					double d2 = entitylivingbase.posZ - parentEntity.posZ;
					parentEntity.renderYawOffset = parentEntity.rotationYaw = (-((float) MathHelper.atan2(d1, d2))
							* 180.0F) / (float) Math.PI;
				}
			}
		}
	}

	static class AIRandomFly extends EntityAIBase {
		private GhostEntity parentEntity;

		public AIRandomFly(GhostEntity ghast) {
			parentEntity = ghast;
			setMutexBits(1);
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean continueExecuting() {
			return false;
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			EntityMoveHelper entitymovehelper = parentEntity.getMoveHelper();

			if (!entitymovehelper.isUpdating()) {
				return true;
			} else {
				double d0 = entitymovehelper.getX() - parentEntity.posX;
				double d1 = entitymovehelper.getY() - parentEntity.posY;
				double d2 = entitymovehelper.getZ() - parentEntity.posZ;
				double d3 = (d0 * d0) + (d1 * d1) + (d2 * d2);
				return (d3 < 2.0D) || (d3 > 3600.0D);
			}
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			if (parentEntity.getAttackTarget() == null) {
				Random random = parentEntity.getRNG();
				double d0 = parentEntity.posX + (((random.nextFloat() * 2.0F) - 1.0F) * 16.0F);
				double d1 = parentEntity.posY + (((random.nextFloat() * 2.0F) - 1.0F) * 16.0F);
				double d2 = parentEntity.posZ + (((random.nextFloat() * 2.0F) - 1.0F) * 16.0F);
				// if the ghost is in the ground move it an open area
				if (((GhostMoveHelper) parentEntity.getMoveHelper()).isNotColliding(parentEntity.posX,
						parentEntity.posY, parentEntity.posZ, 0.3D)) {
					double y = parentEntity.posY;
					while ((parentEntity.worldObj.getBlockState(new BlockPos(d0, y, d2)).getBlock() == Blocks.air)
							&& (y < parentEntity.worldObj.getActualHeight())) {
						y += 1;
					}
					d1 = Math.max(d1, y);
				}
				parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 0.3D);
			} else {
				BlockPos pos = parentEntity.getAttackTarget().getPosition()
						.offset(parentEntity.getAttackTarget().getHorizontalFacing().getOpposite());
				parentEntity.getMoveHelper().setMoveTo(pos.getX(), pos.getY(), pos.getZ(), 0.3D);
			}
		}
	}

	static class GhostMoveHelper extends EntityMoveHelper {
		private GhostEntity parentEntity;
		private int courseChangeCooldown;

		public GhostMoveHelper(GhostEntity ghost) {
			super(ghost);
			parentEntity = ghost;
		}

		/**
		 * Checks if entity bounding box is not colliding with terrain
		 */
		private boolean isNotColliding(double x, double y, double z, double speed) {
			double d0 = (x - parentEntity.posX) / speed;
			double d1 = (y - parentEntity.posY) / speed;
			double d2 = (z - parentEntity.posZ) / speed;
			AxisAlignedBB axisalignedbb = parentEntity.getEntityBoundingBox();

			for (int i = 1; i < speed; ++i) {
				axisalignedbb = axisalignedbb.offset(d0, d1, d2);

				if (!parentEntity.worldObj.getCollidingBoundingBoxes(parentEntity, axisalignedbb).isEmpty()) {
					return false;
				}
			}

			return true;
		}

		@Override
		public void onUpdateMoveHelper() {
			if (update) {
				double d0 = posX - parentEntity.posX;
				double d1 = posY - parentEntity.posY;
				double d2 = posZ - parentEntity.posZ;
				double d3 = (d0 * d0) + (d1 * d1) + (d2 * d2);

				if (courseChangeCooldown-- <= 0) {
					courseChangeCooldown += parentEntity.getRNG().nextInt(5) + 2;
					d3 = MathHelper.sqrt_double(d3);

					if (this.isNotColliding(posX, posY, posZ, d3)) {
						parentEntity.motionX += (d0 / d3) * 0.1D;
						parentEntity.motionY += (d1 / d3) * 0.1D;
						parentEntity.motionZ += (d2 / d3) * 0.1D;
					} else {
						update = false;
					}
				}
			}
		}
	}

	private int followPlayerCooldown = 0;
	private float alpha;

	public GhostEntity(World par1World) {
		super(par1World);

		setSize(0.5F, 0.5F);
		((PathNavigateGround) getNavigator()).setAvoidsWater(false);
		noClip = true;
		alpha = 0.1f;

		moveHelper = new GhostEntity.GhostMoveHelper(this);
		tasks.addTask(5, new GhostEntity.AIRandomFly(this));
		tasks.addTask(7, new GhostEntity.AILookAround(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100000);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.05000000149011612D);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0D);
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected boolean canDespawn() {
		if (isNoDespawnRequired()) {
			return false;
		}
		return true;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	public float getAlpha() {
		if (getAttackTarget() != null) {
			return (float) Math.max((1 - (getPosition().distanceSq(getAttackTarget().getPosition()) / 50)), alpha);
		}
		return alpha;
	}

	@Override
	public boolean getCanSpawnHere() {
		return false;
	}

	@Override
	protected String getLivingSound() {
		if (worldObj.rand.nextInt(10) == 0) {
			return "dynfixins:mob.ghost.ambient";
		}
		return null;
	}

	@Override
	public String getName() {
		return "Ghost";
	}

	@Override
	protected float getSoundPitch() {
		return 1F;
	}

	@Override
	protected float getSoundVolume() {
		return 0.15F;
	}

	@Override
	public boolean isAIDisabled() {
		return false;
	}

	/**
	 * Return the max target range of the entiity (16 by default)
	 */
	protected double maxTargetRange() {
		IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.followRange);
		return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (getAttackTarget() == null) {
			if (followPlayerCooldown-- <= 0) {
				double d0 = maxTargetRange();
				EntityLivingBase entity = worldObj.findNearestEntityWithinAABB(EntityPlayer.class,
						getEntityBoundingBox().expand(d0, d0, d0), this);
				if (entity != null) {
					setAttackTarget(entity);
					followPlayerCooldown = getRNG().nextInt(50) + 200;
				} else {
					followPlayerCooldown = 0;
				}

			}
		} else {
			if (followPlayerCooldown-- <= 0) {
				setAttackTarget(null);
				followPlayerCooldown = getRNG().nextInt(50) + 200;
			}
		}

		if (worldObj.isDaytime() && !worldObj.isRemote) {
			float f = getBrightness(1.0F);
			BlockPos blockpos = new BlockPos(posX, Math.round(posY), posZ);

			if ((f > 0.5F) && ((rand.nextFloat() * 30.0F) < ((f - 0.4F) * 2.0F)) && worldObj.canSeeSky(blockpos)) {
				setDead();
			}
		}
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
}
