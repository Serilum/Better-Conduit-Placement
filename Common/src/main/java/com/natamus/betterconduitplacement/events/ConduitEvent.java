package com.natamus.betterconduitplacement.events;

import com.natamus.betterconduitplacement.config.ConfigHandler;
import com.natamus.betterconduitplacement.util.Util;
import com.natamus.collective.functions.BlockFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ConduitEvent {
	public static InteractionResult onWaterClick(Player player, Level world, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (world.isClientSide) {
			return InteractionResult.PASS;
		}
		
		if (!itemstack.getItem().equals(Items.CONDUIT)) {
			return InteractionResult.PASS;
		}
		
		Vec3 look = player.getLookAngle();
		float distance = 2.0F;
		double dx = player.getX() + (look.x * distance);
		double dy = player.getY() + (look.y * distance) + 2;
		double dz = player.getZ() + (look.z * distance);
		
		BlockPos frontpos = BlockPos.containing(dx, dy, dz);
		
		if (!world.getBlockState(frontpos).getBlock().equals(Blocks.WATER)) {
			return InteractionResult.PASS;
		}
		
		world.setBlockAndUpdate(frontpos, Blocks.CONDUIT.defaultBlockState());

		if (!player.isCreative()) {
			itemstack.shrink(1);
		}

		return InteractionResult.SUCCESS;
	}
	
	public static boolean onConduitClick(Level world, Player player, InteractionHand hand, BlockPos cpos, BlockHitResult hitVec) {
		if (world.isClientSide) {
			return true;
		}

		if (!world.getBlockState(cpos).getBlock().equals(Blocks.CONDUIT)) {
			return true;
		}
		
		ItemStack handstack = player.getItemInHand(hand);
		if (!BlockFunctions.isOneOfBlocks(Util.conduitblocks, handstack)) {
			return true;
		}
		
		boolean set = false;
		while (handstack.getCount() > 0) {
			BlockPos nextpos = Util.getNextLocation(world, cpos);
			if (nextpos == null) {
				break;
			}
			
			Block block = world.getBlockState(nextpos).getBlock();
			if (ConfigHandler.dropReplacedBlockTopConduit) {
				if (!block.equals(Blocks.AIR) && !block.equals(Blocks.WATER)) {
					ItemEntity ei = new ItemEntity(world, cpos.getX(), cpos.getY()+1, cpos.getZ(), new ItemStack(block, 1));
					world.addFreshEntity(ei);
				}
			}
			
			world.setBlockAndUpdate(nextpos, Block.byItem(handstack.getItem()).defaultBlockState());

			if (!player.isCreative()) {
				handstack.shrink(1);
			}
			
			set = true;
			if (!player.isShiftKeyDown()) {
				break;
			}
		}

		return !set;
	}
	
	public static void onBlockBreak(Level world, Player player, BlockPos bpos, BlockState state, BlockEntity blockEntity) {
		if (world.isClientSide) {
			return;
		}
		
		if (!ConfigHandler.breakConduitBlocks) {
			return;
		}
		
		if (!state.getBlock().equals(Blocks.CONDUIT)) {
			return;
		}
		
		Util.destroyAllConduitBlocks(world, bpos);
	}
}