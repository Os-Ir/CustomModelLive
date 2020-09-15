package com.osir.custommodellive.gui.entry;

import java.util.function.Supplier;

import com.osir.custommodellive.gui.GuiConfig;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;

public class SupplierEntry implements IGuiConfigEntry {
	protected boolean isUnlocalized;
	protected Supplier<String> supplier;

	public SupplierEntry(boolean isUnlocalized, Supplier<String> supplier) {
		this.isUnlocalized = isUnlocalized;
		this.supplier = supplier;
	}

	@Override
	public void draw(GuiConfig gui, FontRenderer font, int x, int y, int mouseX, int mouseY, float partialTicks) {
		if (this.isUnlocalized) {
			gui.drawString(font, I18n.format(this.supplier.get()), x, y, 0xffffff);
		} else {
			gui.drawString(font, this.supplier.get(), x, y, 0xffffff);
		}
	}

	@Override
	public int getwidth(GuiConfig gui, FontRenderer font) {
		if (this.isUnlocalized) {
			return font.getStringWidth(I18n.format(this.supplier.get()));
		}
		return font.getStringWidth(this.supplier.get());
	}

	@Override
	public void onClicked(GuiConfig gui, FontRenderer font, int clickX, int clickY) {

	}
}