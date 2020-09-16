package com.osir.custommodellive.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

public class ImageFileTexture extends AbstractTexture {
	protected BufferedImage image;

	public ImageFileTexture(File file) {
		try {
			this.image = TextureUtil.readBufferedImage(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ImageFileTexture(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void loadTexture(IResourceManager resourceManager) throws IOException {
		this.deleteGlTexture();
		TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), this.image, false, false);
	}

	public static void bindTexture(int id) {
		GlStateManager.bindTexture(id);
	}

	public static void deleteTexture(int id) {
		TextureUtil.deleteTexture(id);
	}

	public static int loadTexture(File file) {
		ITextureObject texture = new ImageFileTexture(file);
		try {
			texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture.getGlTextureId();
	}

	public static int loadTexture(BufferedImage image) {
		ITextureObject texture = new ImageFileTexture(image);
		try {
			texture.loadTexture(Minecraft.getMinecraft().getResourceManager());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture.getGlTextureId();
	}
}