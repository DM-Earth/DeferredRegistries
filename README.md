<div align="center">
  <img src="https://github.com/DM-Earth/DeferredRegistries/blob/1.18/icon.png?raw=true" width = 250 alt="Deferred Registries">
  </img>
</div>

<h1 align="center"> Deferred Registries </h1>

This Minecraft mod provides a way to register things that are not available at the time of registration, which is similar to how Minecraft Forge's `DeferredRegister` works.

### Setup

```groovy
repositories {
	// [...]
	maven { url 'https://jitpack.io' }
}

dependencies {
	// [...]
	modImplementation "com.github.DM-Earth:DeferredRegistries:1.18-SNAPSHOT"
}
```

### Usage

First, create a `DeferredRegistries` object, here we use `Item` as example:

```java
private static final DeferredRegistries<Item> ITEMS = DeferredRegistries.create(Registry.ITEM, "example_mod");
```

Then, register things to it:

```java
public static final Item EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Settings()));
public static final Item SIMPLE_ITEM = ITEMS.register("simple_item", new Item(new Item.Settings()));
```

Finally, register the DeferredRegistries object when initializing your mod:

```java
@Override
public void onInitialize() {
    ITEMS.register();
}
```
