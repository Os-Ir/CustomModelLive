package com.osir.custommodellive.gui;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.osir.custommodellive.Main;
import com.osir.custommodellive.model.ModelCustom;
import com.osir.custommodellive.model.RenderCustom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Loader;

public class PlayerRenderer {
	public static final PlayerRenderer INSTANCE = new PlayerRenderer();

	public static final ModelPlayer MODEL = new ModelCustom(0, false);
	private static WeakReference<EntityPlayer> playerCache = null;

	private static final RenderPlayer RENDER = new RenderCustom(Minecraft.getMinecraft().getRenderManager());

	public boolean enable, useSkin, skinLoaded;
	public int posX, posY, scale, mouseX, mouseY, skinId;
	public File file;

	private PlayerRenderer() {
		this.posX = 410;
		this.posY = 350;
		this.scale = 100;
		this.enable = true;
		this.loadConfig();
	}

	public AbstractClientPlayer getPlayer() {
		if (playerCache == null || playerCache.get() == null) {
			playerCache = new WeakReference<EntityPlayer>(Minecraft.getMinecraft().player);
		}
		return (AbstractClientPlayer) playerCache.get();
	}

	public void loadConfig() {
		Path path = Loader.instance().getConfigDir().toPath().resolve(Main.MODID).resolve("config.json");
		if (!Files.exists(path)) {
			return;
		}
		String data = null;
		try {
			data = new String(Files.readAllBytes(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (data == null) {
			return;
		}
		this.useConfigData(new Gson().fromJson(data, ConfigData.class));
	}

	public void saveConfig() {
		Path path = Loader.instance().getConfigDir().toPath().resolve(Main.MODID);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		path = path.resolve("config.json");
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String data = new Gson().toJson(this.getConfigData());
		try {
			Files.write(path, data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean bindSkin() {
		if (this.file == null || !this.file.exists()) {
			return false;
		}
		if (!this.skinLoaded) {
			this.skinId = ImageFileTexture.loadTexture(this.file);
			this.skinLoaded = true;
		}
		ImageFileTexture.bindTexture(this.skinId);
		return true;
	}

	public void renderPlayer(int mouseX, int mouseY, boolean guiOpened) {
		if (!this.enable) {
			return;
		}
		AbstractClientPlayer player = this.getPlayer();
		if (player == null || player.isRiding()) {
			return;
		}
		float rotateX = MathHelper.clamp((380 - mouseX) / 10, -10, 10);
		float rotateY = 0;
		if (guiOpened) {
			rotateY = MathHelper.clamp((160 - mouseY) / 10, -10, 10);
		} else {
			rotateY = (float) MathHelper.clamp(Math.asin(player.getLookVec().y) / Math.PI * 45, -10, 10);
		}
		this.renderModel(player, rotateX, rotateY);
	}

	private void renderModel(AbstractClientPlayer player, float rotateX, float rotateY) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) this.posX, (float) this.posY, 50);
		GlStateManager.scale((float) (-this.scale), (float) this.scale, (float) this.scale);
		GlStateManager.rotate(180, 0, 0, 1);
		float f = player.renderYawOffset;
		float f1 = player.rotationYaw;
		float f2 = player.rotationPitch;
		float f3 = player.prevRotationYawHead;
		float f4 = player.rotationYawHead;
		GlStateManager.rotate(135, 0, 1, 0);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135, 0, 1, 0);
		GlStateManager.rotate(-((float) Math.atan((double) (rotateX / 40))) * 20, 1, 0, 0);
		player.renderYawOffset = (float) Math.atan((double) (rotateX / 40)) * 20;
		player.rotationYaw = (float) Math.atan((double) (rotateX / 40)) * 40;
		player.rotationPitch = -((float) Math.atan((double) (rotateY / 40))) * 20;
		player.rotationYawHead = player.rotationYaw;
		player.prevRotationYawHead = player.rotationYaw;
		GlStateManager.translate(0, 0, 0);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180);
		rendermanager.setRenderShadow(false);
		RENDER.doRender(player, 0, 0, 0, 0, 1);
		rendermanager.setRenderShadow(true);
		player.renderYawOffset = f;
		player.rotationYaw = f1;
		player.rotationPitch = f2;
		player.prevRotationYawHead = f3;
		player.rotationYawHead = f4;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	public ConfigData getConfigData() {
		ConfigData data = new ConfigData();
		data.enable = this.enable;
		data.useSkin = this.useSkin;
		data.posX = this.posX;
		data.posY = this.posY;
		data.scale = this.scale;
		data.file = this.file.getAbsolutePath();
		return data;
	}

	public void useConfigData(ConfigData data) {
		this.enable = data.enable;
		this.useSkin = data.useSkin;
		this.posX = data.posX;
		this.posY = data.posY;
		this.scale = data.scale;
		this.file = new File(data.file);
	}

	public static class ConfigData {
		public boolean enable, useSkin;
		public int posX, posY, scale;
		public String file;
	}
}