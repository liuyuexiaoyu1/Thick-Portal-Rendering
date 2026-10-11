package com.liuyue.thickPortal.mixins;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TheEndPortalBlockEntity.class)
public class EndPortalBlockEntityMixin {
	@Inject(method = "shouldRenderFace", at = @At(value = "HEAD"), cancellable = true, require = 0)
	private void thickportal$hideVanillaEndPortalFaces(Direction direction, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}
}
