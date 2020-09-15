package com.osir.custommodellive.model;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;

public class ModelCustom extends ModelPlayer {
	public ModelCustom(float modelSize, boolean smallArmsIn) {
		super(modelSize, smallArmsIn);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale) {
		this.swingProgress = 0;
		this.isSneak = false;
		this.isRiding = false;
		this.isChild = false;
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.bipedHead.render(scale);
		this.bipedBody.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftLeg.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedHeadwear.render(scale);
		this.bipedBodyWear.render(scale);
		this.bipedLeftArmwear.render(scale);
		this.bipedRightArmwear.render(scale);
		this.bipedLeftLegwear.render(scale);
		this.bipedRightLegwear.render(scale);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		this.bipedHead.rotateAngleY = (float) (netHeadYaw * Math.PI / 180);
		this.bipedHead.rotateAngleX = (float) (headPitch * Math.PI / 180);
		this.bipedRightArm.rotationPointZ = 0.0F;
		this.bipedRightArm.rotationPointX = -5.0F;
		this.bipedLeftArm.rotationPointZ = 0.0F;
		this.bipedLeftArm.rotationPointX = 5.0F;
		this.bipedRightArm.rotateAngleZ = (float) (Math.PI / 25);
		this.bipedLeftArm.rotateAngleZ = -(float) (Math.PI / 25);
		copyModelAngles(this.bipedHead, this.bipedHeadwear);
		copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
		copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
		copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
		copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
		copyModelAngles(this.bipedBody, this.bipedBodyWear);
	}
}