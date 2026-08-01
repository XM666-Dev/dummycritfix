package com.xm666.dummycritfix.mixin.apothic_attributes;

import com.llamalad7.mixinextras.sugar.Local;
import com.xm666.dummycritfix.CritInfo;
import dev.shadowsoffire.apothic_attributes.impl.AttributeEvents;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ApothCritMixin {
    @Mixin(AttributeEvents.class)
    private static class AttributeEventsMixin {
        @Inject(method = "apothCriticalStrike", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/network/PacketDistributor;sendToPlayersTrackingChunk(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;[Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V"))
        private void onApothCriticalStrike(LivingIncomingDamageEvent event, CallbackInfo ci, @Local(name = "damage") float damage) {
            var critInfo = (CritInfo) event.getSource();
            var multiplier = damage / event.getAmount();
            critInfo.dummycritfix$apply(multiplier);
        }
    }
}
