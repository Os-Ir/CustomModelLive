package com.osir.custommodellive.gui;

import com.osir.custommodellive.Main;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

public class GuiConfigTexture extends GuiConfig {
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int guiX = this.width / 2 - 128;
		int guiY = this.height / 2 - 100;
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.drawString(this.fontRenderer, "texture", guiX + 74, guiY + 19, 0xffffff);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		EntityPlayer player = PlayerRenderer.INSTANCE.getPlayer();
		switch (button.id) {
		case 1:
			player.openGui(Main.instance, 2, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
			break;
		}
	}
}