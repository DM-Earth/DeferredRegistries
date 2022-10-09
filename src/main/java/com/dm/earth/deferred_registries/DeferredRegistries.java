package com.dm.earth.deferred_registries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

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
            entry.register(this.registry);
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