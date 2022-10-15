package com.dm.earth.deferred_registries;

import java.util.function.Supplier;

import com.dm.earth.deferred_registries.helper.BiomeRegistryHelper;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class DeferredObject<T> {
    private final Identifier id;
    private final T entry;

    public DeferredObject(Identifier id, T entry) {
        this.id = id;
        this.entry = entry;
    }

    public DeferredObject(Identifier id, Supplier<T> entry) {
        this.id = id;
        this.entry = entry.get();
    }

    public Identifier getId() {
        return this.id;
    }

    public T get() {
        return this.entry;
    }

    public void register(Registry<? super T> registry) {
        Registry.register(registry, this.id, this.entry);
    }

    public void register(RegistryKey<Registry<? super T>> registryKey) {
        if (BiomeRegistryHelper.isBiomeKey(registryKey)) BiomeRegistryHelper.registerBiome((Biome) this.entry, this.id);
    }

    public static <T> DeferredObject<T> of(Identifier id, T entry) {
        return new DeferredObject<>(id, entry);
    }

    public static <T> DeferredObject<T> of(Identifier id, Supplier<T> entry) {
        return new DeferredObject<>(id, entry);
    }
}
