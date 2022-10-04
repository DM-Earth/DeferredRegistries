package com.dm.earth.modid;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Modid implements ModInitializer {
	public static final String MODID = "modid";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
	}

	public static Identifier genIdentifier(String id) {
		return new Identifier(MODID, id);
	}
}
