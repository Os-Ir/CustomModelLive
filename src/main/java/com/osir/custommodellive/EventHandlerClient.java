package com.osir.custommodellive;

import com.osir.custommodellive.gui.GuiModelConfig;
import com.osir.custommodellive.gui.PlayerRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = Main.MODID, value = Side.CLIENT)
public class EventHandlerClient {
	@SubscribeEvent
	public static void render(RenderGameOverlayEvent.Post e) {
		if (e.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE
				&& Minecraft.getMinecraft().currentScreen == null) {
			int width = e.getResolution().getScaledWidth();
			int height = e.getResolution().getScaledHeight();
			PlayerRenderer.INSTANCE.renderPlayer(width, height, PlayerRenderer.INSTANCE.mouseX,
					PlayerRenderer.INSTANCE.mouseY);
		}
	}

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent e) {
		if (GuiModelConfig.KEY.isPressed()) {
			EntityPlayer player = PlayerRenderer.INSTANCE.getPlayer();
			player.openGui(Main.instance, 0, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
	}
}