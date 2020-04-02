package com.osir.custommodellive.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;

public class GuiModelConfig extends GuiScreen {
	public static final KeyBinding KEY = new KeyBinding("key.custommodellive.config", Keyboard.KEY_V,
			"key.custommodellive.category");

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		PlayerRenderer.INSTANCE.mouseX = mouseX;
		PlayerRenderer.INSTANCE.mouseY = mouseY;
		PlayerRenderer.INSTANCE.renderPlayer(this.width, this.height, mouseX, mouseY);
		this.fontRenderer.drawString("X: " + PlayerRenderer.INSTANCE.posX, 20, 20, 0);
		this.fontRenderer.drawString("Y: " + PlayerRenderer.INSTANCE.posY, 20, 30, 0);
		this.fontRenderer.drawString("Scale: " + PlayerRenderer.INSTANCE.scale + "%", 20, 40, 0);
	}

	@Override
	public void handleKeyboardInput() throws IOException {
		super.handleKeyboardInput();
		int key = Keyboard.getEventKey();
		int move = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54) ? 100 : 10;
		int scale = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54) ? 10 : 1;
		if (Keyboard.isKeyDown(key)) {
			switch (key) {
			case 24:
				PlayerRenderer.INSTANCE.use = !PlayerRenderer.INSTANCE.use;
				break;
			case 203:
				PlayerRenderer.INSTANCE.posX -= move;
				break;
			case 205:
				PlayerRenderer.INSTANCE.posX += move;
				break;
			case 200:
				PlayerRenderer.INSTANCE.posY -= move;
				break;
			case 208:
				PlayerRenderer.INSTANCE.posY += move;
				break;
			case 12:
				PlayerRenderer.INSTANCE.scale -= scale;
				break;
			case 13:
				PlayerRenderer.INSTANCE.scale += scale;
				break;
			default:
				break;
			}
		}
	}
}