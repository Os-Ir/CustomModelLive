package com.osir.custommodellive.model;

import com.osir.custommodellive.gui.PlayerRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderCustom extends RenderPlayer {
	public RenderCustom(RenderManager renderManager) {
		super(renderManager);
		this.mainModel = PlayerRenderer.MODEL;
	}

	@Override
	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		if (entity.isSneaking()) {
			y += 0.125;
		}
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected void renderLayers(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) {
		if (entitylivingbaseIn.isSneaking()) {
			GlStateManager.translate(0, -0.2F, 0);
		}
		super.renderLayers(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw,
				headPitch, scaleIn);
	}

	@Override
	protected boolean bindEntityTexture(AbstractClientPlayer entity) {
		if (PlayerRenderer.INSTANCE.useSkin && PlayerRenderer.INSTANCE.bindSkin()) {
			return true;
		}
		ResourceLocation resourcelocation = this.getEntityTexture(entity);
		if (resourcelocation == null) {
			return false;
		} else {
			this.bindTexture(resourcelocation);
			return true;
		}
	}

	@Override
	protected void renderModel(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		boolean flag = this.isVisible(entitylivingbaseIn);
		boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);
		if (flag || flag1) {
			if (!this.bindEntityTexture(entitylivingbaseIn)) {
				return;
			}
			if (flag1) {
				GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
			PlayerRenderer.MODEL.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
					headPitch, scaleFactor);
			if (flag1) {
				GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
			}
		}
	}
}