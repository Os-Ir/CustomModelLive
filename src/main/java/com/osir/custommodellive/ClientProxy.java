package com.osir.custommodellive;

import com.osir.custommodellive.gui.GuiHandler;
import com.osir.custommodellive.gui.GuiModelConfig;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
		ClientRegistry.registerKeyBinding(GuiModelConfig.KEY);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
}