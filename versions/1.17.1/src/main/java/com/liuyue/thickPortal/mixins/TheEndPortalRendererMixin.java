package com.liuyue.thickPortal.mixins;

import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(TheEndPortalRenderer.class)
public abstract class TheEndPortalRendererMixin {
	@ModifyArgs(
			method = "renderCube",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/blockentity/TheEndPortalRenderer;renderFace(Lnet/minecraft/world/level/block/entity/TheEndPortalBlockEntity;Lcom/mojang/math/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFLnet/minecraft/core/Direction;)V"
			)
	)
	private void thickportal$squeezeIntoPortalShape(Args args) {
		Object direction = args.get(args.size() - 1);
		if (direction == Direction.UP || direction == Direction.DOWN) {
			return;
		}
		args.set(5, 0.375F * (Float) args.get(5) + 0.375F);
		args.set(6, 0.375F * (Float) args.get(6) + 0.375F);
	}
}
