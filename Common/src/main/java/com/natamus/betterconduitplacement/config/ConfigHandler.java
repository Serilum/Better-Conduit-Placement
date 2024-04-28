package com.natamus.betterconduitplacement.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.betterconduitplacement.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean breakConduitBlocks = true;
	@Entry public static boolean dropReplacedBlockTopConduit = true;

	public static void initConfig() {
		configMetaData.put("breakConduitBlocks", Arrays.asList(
			"If enabled, drops all conduit blocks when the conduit itself is broken."
		));
		configMetaData.put("dropReplacedBlockTopConduit", Arrays.asList(
			"If enabled, when prismarine replaces a normal block that block is dropped on top of the conduit."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}