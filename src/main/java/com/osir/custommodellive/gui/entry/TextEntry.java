package com.osir.custommodellive.gui.entry;

import com.osir.custommodellive.gui.GuiConfig;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

public class TextEntry implements IGuiConfigEntry {
	protected boolean isUnlocalized;
	protected String text;

	public TextEntry(String text) {
		this(false, text);
	}

	public TextEntry(boolean isUnlocalized, String text) {
		this.isUnlocalized = isUnlocalized;
		this.text = text;
	}

	@Override
	public void draw(GuiConfig gui, FontRenderer font, int x, int y, int mouseX, int mouseY, float partialTicks) {
		if (this.isUnlocalized) {
			gui.drawString(font, I18n.format(this.text), x, y, 0xffffff);
		} else {
			gui.drawString(font, this.text, x, y, 0xffffff);
		}
	}

	@Override
	public int getwidth(GuiConfig gui, FontRenderer font) {
		if (this.isUnlocalized) {
			return font.getStringWidth(I18n.format(this.text));
		}
		return font.getStringWidth(this.text);
	}

	@Override
	public void onClicked(GuiConfig gui, FontRenderer font, int clickX, int clickY) {

	}
}