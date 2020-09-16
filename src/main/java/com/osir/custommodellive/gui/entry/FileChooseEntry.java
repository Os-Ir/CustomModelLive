package com.osir.custommodellive.gui.entry;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import com.osir.custommodellive.gui.GuiConfig;
import com.osir.custommodellive.gui.PlayerRenderer;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class FileChooseEntry implements IGuiConfigEntry {
	@Override
	public void draw(GuiConfig gui, FontRenderer font, int x, int y, int mouseX, int mouseY, float partialTicks) {
		gui.mc.renderEngine.bindTexture(GuiConfig.BACKGROUND);
		GlStateManager.color(1, 1, 1, 1);
		gui.drawTexturedModalRect(x, y, 0, 242, 9, 9);
	}

	@Override
	public int getwidth(GuiConfig gui, FontRenderer font) {
		return 9;
	}

	@Override
	public void onClicked(GuiConfig gui, FontRenderer font, int clickX, int clickY) {
		new Thread(() -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.showDialog(new JLabel(), I18n.format("custommodellive.config.texture.choose"));
			File file = chooser.getSelectedFile();
			if (file != null) {
				PlayerRenderer.INSTANCE.file = file;
				PlayerRenderer.INSTANCE.enable = true;
			}
		}).start();
	}
}