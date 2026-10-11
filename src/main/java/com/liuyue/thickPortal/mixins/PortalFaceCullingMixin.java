package com.liuyue.thickPortal.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class PortalFaceCullingMixin {
	@Inject(method = "shouldRenderFace", at = @At(value = "HEAD"), cancellable = true)
	private static void thickportal$cullSharedFace(BlockState state, BlockGetter level, BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir) {
		Block block = state.getBlock();

		if (block == Blocks.NETHER_PORTAL) {
			Direction.Axis axis = state.getValue(BlockStateProperties.HORIZONTAL_AXIS);
			Direction.Axis faceAxis = side.getAxis();
			boolean largeFace = axis == Direction.Axis.X ? faceAxis == Direction.Axis.Z : faceAxis == Direction.Axis.X;

			if (!largeFace && level.getBlockState(pos.relative(side)).getBlock() == block) {
				cir.setReturnValue(false);
			}
			return;
		}

		if (block == Blocks.END_PORTAL) {
			if (level.getBlockState(pos.relative(side)).getBlock() == block) {
				cir.setReturnValue(false);
			}
		}
	}

}
