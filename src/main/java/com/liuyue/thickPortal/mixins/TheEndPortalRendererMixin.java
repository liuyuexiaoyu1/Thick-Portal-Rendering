package com.liuyue.thickPortal.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TheEndPortalRenderer.class)
public abstract class TheEndPortalRendererMixin {
	@Unique
	private Direction thickportal$currentFace;

	@WrapOperation(
			method = "render(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;DDDFI)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;shouldRenderFace(Lnet/minecraft/core/Direction;)Z"
			)
	)
	private boolean thickportal$trackCurrentFace(TheEndPortalBlockEntity entity, Direction direction, Operation<Boolean> original) {
		this.thickportal$currentFace = direction;
		return original.call(entity, direction);
	}

	@WrapOperation(
			method = "render(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;DDDFI)V",
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/BufferBuilder;"
			)
	)
	private BufferBuilder thickportal$squeezeSides(
			BufferBuilder buffer, double x, double y, double z,
			Operation<BufferBuilder> original,
			TheEndPortalBlockEntity entity, double camX, double camY, double camZ, float partialTicks, int destroyStage
	) {
		if (this.thickportal$currentFace == Direction.UP) {
			return original.call(buffer, x, y, z);
		}

		double localY = y - camY;
		return original.call(buffer, x, camY + 0.75D * localY, z);
	}
}
