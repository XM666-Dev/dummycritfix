package com.xm666.dummycritfix.mixin.apothic_attributes;

import com.llamalad7.mixinextras.sugar.Local;
import com.xm666.dummycritfix.CritInfo;
import dev.shadowsoffire.attributeslib.impl.AttributeEvents;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ApothCritMixin {
    @Mixin(value = AttributeEvents.class, remap = false)
    private static class AttributeEventsMixin {
        @Inject(method = "apothCriticalStrike", at = @At(value = "INVOKE", target = "Ldev/shadowsoffire/placebo/network/PacketDistro;sendToTracking(Lnet/minecraftforge/network/simple/SimpleChannel;Ljava/lang/Object;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V", ordinal = 0))
        private void onApothCriticalStrike(LivingHurtEvent event, CallbackInfo ci, @Local(name = "critMult") float critMult) {
            var critInfo = (CritInfo) event.getSource();
            critInfo.dummycritfix$apply(critMult);
        }
    }
}
