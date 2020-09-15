package com.osir.custommodellive.gui.entry;

import com.osir.custommodellive.gui.GuiConfig;

import net.minecraft.client.gui.FontRenderer;

public interface IGuiConfigEntry {
	void draw(GuiConfig gui, FontRenderer font, int x, int y, int mouseX, int mouseY, float partialTicks);

	int getwidth(GuiConfig gui, FontRenderer font);
	
	void onClicked(GuiConfig gui, FontRenderer font, int clickX, int clickY);
}