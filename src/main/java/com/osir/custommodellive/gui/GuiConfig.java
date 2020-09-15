package com.osir.custommodellive.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import com.osir.custommodellive.Main;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class GuiConfig extends GuiScreen {
	public static final ResourceLocation BACKGROUND = new ResourceLocation(Main.MODID,
			"textures/gui/gui_model_config.png");

	public static final KeyBinding KEY = new KeyBinding("key.custommodellive.config", Keyboard.KEY_V,
			"key.custommodellive.category");

	public static final List<Pair<String, List<GuiConfigGroup>>> CONFIG_GROUP = new ArrayList<Pair<String, List<GuiConfigGroup>>>();

	public static final int BUTTON_MOVE_TIME = 150;

	private int openGui = -1;
	private long onButtonId = -1, onButtonTime;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		PlayerRenderer.INSTANCE.mouseX = mouseX;
		PlayerRenderer.INSTANCE.mouseY = mouseY;
		PlayerRenderer.INSTANCE.renderPlayer(mouseX, mouseY, true);
		this.drawDefaultBackground();
		int guiX = this.width / 2 - 128;
		int guiY = this.height / 2 - 100;
		this.mc.renderEngine.bindTexture(BACKGROUND);
		int buttonId = this.getMouseOnButton(mouseX - guiX, mouseY - guiY);
		if (buttonId != this.onButtonId) {
			this.onButtonId = buttonId;
			if (buttonId != -1) {
				this.onButtonTime = System.currentTimeMillis();
			} else {
				this.onButtonTime = 0;
			}
		}
		int move = 0;
		if (this.onButtonId != -1) {
			float time = MathHelper.clamp(System.currentTimeMillis() - this.onButtonTime, 0, BUTTON_MOVE_TIME);
			move = (int) (time / BUTTON_MOVE_TIME * 68);
		}
		this.drawTexturedModalRect(guiX, guiY, 0, 0, 256, 200);
		for (int i = 0; i < this.buttonList.size(); i++) {
			GuiButton button = this.buttonList.get(i);
			if (this.onButtonId == i) {
				if (move != 0) {
					this.drawTexturedModalRect(guiX + 2, guiY + 17 + i * 15, 0, 216, move, 15);
				}
				if (move != 68) {
					this.drawTexturedModalRect(guiX + 2 + move, guiY + 17 + i * 15, move, 201, 68 - move, 15);
				}
			} else {
				this.drawTexturedModalRect(guiX + 2, guiY + 17 + i * 15, 0, 201, 68, 15);
			}
		}
		this.drawCenteredString(this.fontRenderer, I18n.format("custommodellive.config"), guiX + 128, guiY + 4,
				0xffffff);
		for (GuiButton button : this.buttonList) {
			this.drawCenteredString(this.fontRenderer, button.displayString, button.x + button.width / 2,
					button.y + (button.height - 8) / 2, 0xffffff);
		}
		if (this.openGui != -1) {
			List<GuiConfigGroup> list = CONFIG_GROUP.get(this.openGui).getRight();
			for (int i = 0; i < list.size(); i++) {
				list.get(i).draw(this, this.fontRenderer, guiX + 77, guiY + 22 + i * 14, mouseX, mouseY, partialTicks);
			}
		}
	}

	protected void drawConfigInfo() {

	}

	private int getMouseOnButton(int mouseX, int mouseY) {
		if (mouseX < 2 || mouseX >= 70 || mouseY < 17) {
			return -1;
		}
		int size = this.buttonList.size();
		int id = (mouseY - 17) / 15;
		return id < size ? id : -1;
	}

	static {
		CONFIG_GROUP
				.add(Pair.of("model",
						Arrays.asList(
								GuiConfigGroup.builder().addText(true, "custommodellive.config.model.use")
										.addSwitch(() -> PlayerRenderer.INSTANCE.enable,
												() -> PlayerRenderer.INSTANCE.enable ^= true)
										.build(),
								GuiConfigGroup.builder().addInformation(true, "custommodellive.config.model.x.tip")
										.addText(true, "custommodellive.config.model.x")
										.addSupplier(false,
												() -> "" + TextFormatting.GREEN + PlayerRenderer.INSTANCE.posX)
										.build(),
								GuiConfigGroup.builder().addInformation(true, "custommodellive.config.model.y.tip")
										.addText(true, "custommodellive.config.model.y")
										.addSupplier(false,
												() -> "" + TextFormatting.GREEN + PlayerRenderer.INSTANCE.posY)
										.build(),
								GuiConfigGroup.builder().addInformation(true, "custommodellive.config.model.scale.tip")
										.addText(true, "custommodellive.config.model.scale")
										.addSupplier(false,
												() -> "" + TextFormatting.GREEN + PlayerRenderer.INSTANCE.scale)
										.build())));
	}

	@Override
	public void initGui() {
		int guiX = this.width / 2 - 128;
		int guiY = this.height / 2 - 100;
		for (int i = 0; i < CONFIG_GROUP.size(); i++) {
			Pair<String, List<GuiConfigGroup>> pair = CONFIG_GROUP.get(i);
			this.buttonList.add(new GuiButton(i, guiX + 2, guiY + 17 + i * 15, 68, 15,
					I18n.format("custommodellive.config." + pair.getLeft())));
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int startX = this.width / 2 - 51;
		int startY = this.height / 2 - 78;
		int idx = (mouseY - startY) / 14;
		int height = (mouseY - startY) % 14;
		List<GuiConfigGroup> list = CONFIG_GROUP.get(this.openGui).getRight();
		if (list.size() > idx && height < 9) {
			list.get(idx).onClicked(this, this.fontRenderer, mouseX - startX, height);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		this.openGui = button.id;
	}

	@Override
	public void handleKeyboardInput() throws IOException {
		super.handleKeyboardInput();
		int key = Keyboard.getEventKey();
		int move = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54) ? 100 : 10;
		int scale = Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54) ? 10 : 1;
		if (Keyboard.isKeyDown(key)) {
			switch (key) {
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