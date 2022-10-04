package com.dm.earth.deferred_registries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DeferredRegistries<T> {
    private final Registry<? super T> registry;
    private final String modId;
    private final List<DeferredObject<T>> entries;

    private DeferredRegistries(Registry<? super T> registry, String modId) {
        this.registry = registry;
        this.modId = modId;
        this.entries = new ArrayList<>();
    }

    public static <T> DeferredRegistries<T> create(Registry<? super T> registry, String modId) {
        return new DeferredRegistries<T>(registry, modId);
    }

    public DeferredObject<T> register(String name, T entry) {
        if (this.entries.contains(entry)) {
            throw new IllegalArgumentException("Entry already exists: " + entry.toString());
        }

        DeferredObject<T> e = new DeferredObject<T>(new Identifier(this.modId, name), entry);
        this.entries.add(e);
        return e;
    }

    public DeferredObject<T> register(String name, Supplier<T> entry) {
        return this.register(name, entry.get());
    }

    public void register() {
        for (DeferredObject<T> entry : entries) {
            entry.register(this.registry);
        }
    }
}