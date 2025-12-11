package com.natamus.betterconduitplacement.forge.events;

import com.natamus.betterconduitplacement.events.ConduitEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeConduitEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeConduitEvent.class);
	}

	@SubscribeEvent
	public static void onWaterClick(PlayerInteractEvent.RightClickItem e) {
		ConduitEvent.onWaterClick(e.getEntity(), e.getLevel(), e.getHand());
	}
	
	@SubscribeEvent
	public static boolean onConduitClick(PlayerInteractEvent.RightClickBlock e) {
		if (!ConduitEvent.onConduitClick(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), e.getHitVec())) {
			return true;
		}
		return false;
	}
	
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}
		
		ConduitEvent.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null);
	}
}
