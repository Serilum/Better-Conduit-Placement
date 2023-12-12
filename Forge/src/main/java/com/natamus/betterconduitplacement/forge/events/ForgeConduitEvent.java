package com.natamus.betterconduitplacement.forge.events;

import com.natamus.betterconduitplacement.events.ConduitEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeConduitEvent {
	@SubscribeEvent
	public void onWaterClick(PlayerInteractEvent.RightClickItem e) {
		ConduitEvent.onWaterClick(e.getPlayer(), e.getWorld(), e.getHand());
	}
	
	@SubscribeEvent
	public void onConduitClick(PlayerInteractEvent.RightClickBlock e) {
		if (!ConduitEvent.onConduitClick(e.getWorld(), e.getPlayer(), e.getHand(), e.getPos(), e.getHitVec())) {
			e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getWorld());
		if (level == null) {
			return;
		}
		
		ConduitEvent.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null);
	}
}
