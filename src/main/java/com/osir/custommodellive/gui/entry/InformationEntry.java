package com.osir.custommodellive.gui.entry;

import com.osir.custommodellive.gui.GuiConfig;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;

public class InformationEntry implements IGuiConfigEntry {
	protected boolean isUnlocalized;
	protected String text;

	public InformationEntry(String text) {
		this(false, text);
	}

	public InformationEntry(boolean isUnlocalized, String text) {
		this.isUnlocalized = isUnlocalized;
		this.text = text;
	}

	@Override
	public void draw(GuiConfig gui, FontRenderer font, int x, int y, int mouseX, int mouseY, float partialTicks) {
		gui.mc.renderEngine.bindTexture(GuiConfig.BACKGROUND);
		GlStateManager.color(1, 1, 1, 1);
		gui.drawTexturedModalRect(x, y, 10, 242, 9, 9);
		if (mouseX >= x && mouseX < x + 9 && mouseY >= y && mouseY < y + 9) {
			if (this.isUnlocalized) {
				gui.drawHoveringText(I18n.format(this.text), x, y);
			} else {
				gui.drawHoveringText(this.text, x, y);
			}
			RenderHelper.disableStandardItemLighting();
		}
	}

	@Override
	public int getwidth(GuiConfig gui, FontRenderer font) {
		return 9;
	}

	@Override
	public void onClicked(GuiConfig gui, FontRenderer font, int clickX, int clickY) {

	}
}