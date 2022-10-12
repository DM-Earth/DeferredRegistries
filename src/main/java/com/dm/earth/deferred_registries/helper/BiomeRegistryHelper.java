package com.dm.earth.deferred_registries.helper;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class BiomeRegistryHelper {
    public static <T> void registerBiome(Biome biome, Identifier id) {
        RegistryKey<Biome> biomeKey = RegistryKey.of(Registry.BIOME_KEY, id);
        BuiltinRegistries.add(BuiltinRegistries.BIOME, biomeKey, biome);
    }

    public static boolean isBiomeKey(RegistryKey<?> registryKey) {
        return registryKey.equals(Registry.BIOME_KEY);
    }
}
