package com.dm.earth.deferred_registries;

import java.util.function.Supplier;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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
    
}
