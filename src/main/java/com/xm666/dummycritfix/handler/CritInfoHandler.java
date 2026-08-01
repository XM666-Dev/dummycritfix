package com.xm666.dummycritfix.handler;

import com.xm666.dummycritfix.CritInfo;
import net.mehvahdjukaar.dummmmmmy.common.CritRecord;
import net.minecraft.world.damagesource.DamageSource;

public class CritInfoHandler {
    public static CritRecord toRecord(DamageSource source, CritRecord fallback) {
        var info = (CritInfo) source;
        return info.dummycritfix$toRecord(fallback);
    }
}
