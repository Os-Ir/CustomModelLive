package com.osir.custommodellive.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;

import com.osir.custommodellive.gui.entry.IGuiConfigEntry;
import com.osir.custommodellive.gui.entry.InformationEntry;
import com.osir.custommodellive.gui.entry.SupplierEntry;
import com.osir.custommodellive.gui.entry.SwitchEntry;
import com.osir.custommodellive.gui.entry.TextEntry;

import net.minecraft.client.gui.FontRenderer;

public class GuiConfigGroup {
	public List<IGuiConfigEntry> entries;

	public GuiConfigGroup(List<IGuiConfigEntry> entries) {
		Validate.notNull(entries);
		this.entries = entries;
	}

	public static GuiConfigGroupBuilder builder() {
		return new GuiConfigGroupBuilder();
	}

	public void draw(GuiConfig gui, FontRenderer font, int x, int y, int mouseX, int mouseY, float partialTicks) {
		int move = x;
		for (IGuiConfigEntry entry : this.entries) {
			entry.draw(gui, font, move, y, mouseX, mouseY, partialTicks);
			move += entry.getwidth(gui, font) + 5;
		}
	}

	public void onClicked(GuiConfig gui, FontRenderer font, int clickX, int clickY) {
		int move = 0;
		for (IGuiConfigEntry entry : this.entries) {
			int width = entry.getwidth(gui, font);
			if (clickX >= move && clickX < move + width) {
				entry.onClicked(gui, font, clickX - move, clickY);
			}
			move += entry.getwidth(gui, font) + 5;
		}
	}

	public static class GuiConfigGroupBuilder {
		private List<IGuiConfigEntry> entries;

		public GuiConfigGroupBuilder() {
			this.entries = new ArrayList<IGuiConfigEntry>();
		}

		public GuiConfigGroupBuilder addText(boolean isUnlocalized, String text) {
			this.entries.add(new TextEntry(isUnlocalized, text));
			return this;
		}

		public GuiConfigGroupBuilder addSupplier(boolean isUnlocalized, Supplier<String> supplier) {
			this.entries.add(new SupplierEntry(isUnlocalized, supplier));
			return this;
		}

		public GuiConfigGroupBuilder addSwitch(BooleanSupplier supplier, Runnable callback) {
			this.entries.add(new SwitchEntry(supplier, callback));
			return this;
		}

		public GuiConfigGroupBuilder addInformation(boolean isUnlocalized, String text) {
			this.entries.add(new InformationEntry(isUnlocalized, text));
			return this;
		}

		public GuiConfigGroup build() {
			return new GuiConfigGroup(this.entries);
		}
	}
}