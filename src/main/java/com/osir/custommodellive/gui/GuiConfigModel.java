package com.osir.custommodellive.gui;

import com.osir.custommodellive.Main;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public class GuiConfigModel extends GuiConfig {
	public static final GuiConfigGroup GROUP = GuiConfigGroup.builder()
			.addText(true, "custommodellive.config.model.use")
			.addSwitch(() -> PlayerRenderer.INSTANCE.enable, () -> PlayerRenderer.INSTANCE.enable ^= true).build();

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int guiX = this.width / 2 - 128;
		int guiY = this.height / 2 - 100;
		super.drawScreen(mouseX, mouseY, partialTicks);
		GROUP.draw(this, this.fontRenderer, guiX + 77, guiY + 22, mouseX, mouseY, partialTicks);
		this.drawString(this.fontRenderer, I18n.format("custommodellive.config.model.x") + " " + TextFormatting.GREEN
				+ PlayerRenderer.INSTANCE.posX, guiX + 85, guiY + 30, 0xffffff);
		this.drawString(this.fontRenderer, I18n.format("custommodellive.config.model.y") + " " + TextFormatting.GREEN
				+ PlayerRenderer.INSTANCE.posY, guiX + 85, guiY + 41, 0xffffff);
		this.drawString(this.fontRenderer, I18n.format("custommodellive.config.model.scale") + " "
				+ TextFormatting.GREEN + PlayerRenderer.INSTANCE.scale, guiX + 85, guiY + 52, 0xffffff);
		GlStateManager.color(1, 1, 1, 1);
		this.drawTipButton(guiX + 74, guiY + 30, mouseX, mouseY, partialTicks, "custommodellive.config.model.x.tip");
		this.drawTipButton(guiX + 74, guiY + 41, mouseX, mouseY, partialTicks, "custommodellive.config.model.y.tip");
		this.drawTipButton(guiX + 74, guiY + 52, mouseX, mouseY, partialTicks,
				"custommodellive.config.model.scale.tip");
	}

	protected void drawTipButton(int x, int y, int mouseX, int mouseY, float partialTicks, String unlocalizedName) {
		this.mc.renderEngine.bindTexture(BACKGROUND);
		this.drawTexturedModalRect(x, y, 10, 242, 9, 9);
		if (mouseX >= x && mouseX < x + 9 && mouseY >= y && mouseY < y + 9) {
			this.drawHoveringText(I18n.format(unlocalizedName), x, y);
			GlStateManager.color(1, 1, 1, 1);
			RenderHelper.disableStandardItemLighting();
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		EntityPlayer player = PlayerRenderer.INSTANCE.getPlayer();
		switch (button.id) {
		case 0:
			player.openGui(Main.instance, 1, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
			break;
		}
	}
}