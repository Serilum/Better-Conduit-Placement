package com.natamus.betterconduitplacement;

import com.natamus.betterconduitplacement.events.ConduitEvent;
import com.natamus.betterconduitplacement.util.Reference;
import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		UseItemCallback.EVENT.register((player, world, hand) -> {
			return ConduitEvent.onWaterClick(player, world, hand);
		});

		CollectiveBlockEvents.BLOCK_RIGHT_CLICK.register((Level world, Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec) -> {
			return ConduitEvent.onConduitClick(world, player, hand, pos, hitVec);
		});

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			ConduitEvent.onBlockBreak(world, player, pos, state, entity);
		});
	}

	private static void setGlobalConstants() {

	}
}
