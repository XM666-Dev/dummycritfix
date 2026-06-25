package com.xm666.dummycritfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.xm666.dummycritfix.CritInfo;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

public class CritInfoMixin {
    @Mixin(Player.class)
    private static class PlayerMixin {
        @ModifyExpressionValue(method = "attack", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/common/CommonHooks;fireCriticalHit(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;ZF)Lnet/neoforged/neoforge/event/entity/player/CriticalHitEvent;"))
        private CriticalHitEvent modifyCriticalHit(CriticalHitEvent original, @Local(name = "damagesource") DamageSource source) {
            ((CritInfo) source).dummycritfix$setCrit(original.getDamageMultiplier(), original.isCriticalHit());
            return original;
        }
    }

    @Mixin(DamageSource.class)
    private static class DamageSourceMixin implements CritInfo {
        @Unique
        private float dummycritfix$dmgMultiplier;
        @Unique
        private boolean dummycritfix$isCriticalHit;

        @Unique
        public void dummycritfix$setCrit(float dmgMultiplier, boolean isCriticalHit) {
            dummycritfix$dmgMultiplier = dmgMultiplier;
            dummycritfix$isCriticalHit = isCriticalHit;
        }

        @Unique
        public float dummycritfix$getDamageMultiplier() {
            return dummycritfix$dmgMultiplier;
        }

        @Unique
        public boolean dummycritfix$isCriticalHit() {
            return dummycritfix$isCriticalHit;
        }
    }
}
