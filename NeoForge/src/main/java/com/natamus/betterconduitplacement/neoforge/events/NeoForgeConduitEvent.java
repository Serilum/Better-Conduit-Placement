package com.natamus.betterconduitplacement.neoforge.events;

import com.natamus.betterconduitplacement.events.ConduitEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeConduitEvent {
	@SubscribeEvent
	public static void onWaterClick(PlayerInteractEvent.RightClickItem e) {
		ConduitEvent.onWaterClick(e.getEntity(), e.getLevel(), e.getHand());
	}
	
	@SubscribeEvent
	public static void onConduitClick(PlayerInteractEvent.RightClickBlock e) {
		if (!ConduitEvent.onConduitClick(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), e.getHitVec())) {
			e.setCanceled(true);
		}
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
