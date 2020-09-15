package com.osir.custommodellive.gui.entry;

import java.util.function.BooleanSupplier;

import com.osir.custommodellive.gui.GuiConfig;

import net.minecraft.client.gui.FontRenderer;

public class SwitchEntry implements IGuiConfigEntry {
	protected BooleanSupplier supplier;
	protected Runnable callback;

	public SwitchEntry(BooleanSupplier supplier, Runnable callback) {
		this.supplier = supplier;
		this.callback = callback;
	}

	@Override
	public void draw(GuiConfig gui, FontRenderer font, int x, int y, int mouseX, int mouseY, float partialTicks) {
		gui.mc.renderEngine.bindTexture(GuiConfig.BACKGROUND);
		gui.drawTexturedModalRect(x, y, 0, 232, 17, 9);
		if (this.supplier.getAsBoolean()) {
			gui.drawTexturedModalRect(x + 9, y + 1, 17, 232, 7, 7);
		} else {
			gui.drawTexturedModalRect(x + 1, y + 1, 24, 232, 7, 7);
		}
	}

	@Override
	public int getwidth(GuiConfig gui, FontRenderer font) {
		return 17;
	}

	@Override
	public void onClicked(GuiConfig gui, FontRenderer font, int clickX, int clickY) {
		this.callback.run();
	}
}