package com.dm.earth.deferred_registries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

public class DeferredRegistries<T> {
    private Registry<? super T> registry;
    private RegistryKey<Registry<? super T>> registryKey;
    private final String modId;
    private final List<DeferredObject<T>> entries;

    public DeferredRegistries(String modId) {
        this.modId = modId;
        this.entries = new ArrayList<>();
        this.registryKey = null;
        this.registry = null;
    }

    public DeferredRegistries<T> registry(Registry<? super T> registry) {
        this.registry = registry;
        return this;
    }

    public DeferredRegistries<T> registryKey(RegistryKey<Registry<? super T>> registryKey) {
        this.registryKey = registryKey;
        return this;
    }

    public static <T> DeferredRegistries<T> create(Registry<? super T> registry, String modId) {
        return new DeferredRegistries<T>(modId).registry(registry);
    }

    public static <T> DeferredRegistries<T> create(RegistryKey<Registry<? super T>> registryKey, String modId) {
        return new DeferredRegistries<T>(modId).registryKey(registryKey);
    }

    public DeferredObject<T> register(String name, T entry) {
        if (this.getKey(entry) != null) throw new IllegalArgumentException("Entry already exists: " + entry.toString());
        DeferredObject<T> e = new DeferredObject<T>(new Identifier(this.modId, name), entry);
        this.entries.add(e);
        return e;
    }

    public DeferredObject<T> register(String name, Supplier<T> entry) {
        return this.register(name, entry.get());
    }

    public void register() {
        for (DeferredObject<T> entry : entries) {
            if (this.registry != null) {
                entry.register(this.registry);
            } else if (this.registryKey != null) {
                entry.register(this.registryKey);
            }
        }
    }

    public Collection<DeferredObject<T>> getObjects() {
        return this.entries;
    }

    public Collection<T> getEntries() {
        List<T> entriesL = new ArrayList<>();
        for (DeferredObject<T> entry : this.entries) {
            entriesL.add(entry.get());
        }
        return entriesL;
    }

    @Nullable
    public Identifier getKey(T entry) {
        for (DeferredObject<T> object : this.entries) {
            if (object.get().equals(entry)) return object.getId();
        }
        return null;
    }

    @Nullable
    public T get(Identifier id) {
        for (DeferredObject<T> object : this.entries) {
            if (object.getId().equals(id)) return object.get();
        }
        return null;
    }

    @Nullable
    public Identifier get(T entry) {
        return this.getKey(entry);
    }
}