package com.osir.custommodellive.gui;

import java.lang.ref.WeakReference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerRenderer {
	public static final PlayerRenderer INSTANCE = new PlayerRenderer();

	private static WeakReference<EntityPlayer> playerCache = null;

	public boolean use;
	public int posX, posY, scale, mouseX, mouseY;

	private PlayerRenderer() {
		this.posX = -60;
		this.posY = 100;
		this.scale = 100;
		this.use = true;
	}

	public EntityPlayer getPlayer() {
		if (playerCache == null || playerCache.get() == null) {
			playerCache = new WeakReference<EntityPlayer>(Minecraft.getMinecraft().player);
		}
		return playerCache.get();
	}

	public void renderPlayer(int width, int height, int mouseX, int mouseY) {
		if (!this.use) {
			return;
		}
		EntityPlayer player = this.getPlayer();
		int x = width + this.posX;
		int y = height + this.posY;
		if (player == null) {
			return;
		}
		int rotateX = Math.max(Math.min((380 - mouseX) / 10, 10), -10);
		int rotateY = Math.max(Math.min((160 - mouseY) / 10, 10), -10);
		GuiInventory.drawEntityOnScreen(x, y, this.scale, rotateX, rotateY, player);
	}
}