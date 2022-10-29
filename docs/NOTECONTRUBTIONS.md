# GlassClient 

# Below is Information useful for contributing to Glass

## Setting up a Workspace
[Click for intructions](WORKSPACE.md)

## Additional Libraries
### Mixins
Mixins can be used to modify classes at runtime before they are loaded. GlassClient uses them to inject its code into the Minecraft client. This way, we do not have to ship Mojang's copyrighted code. If you want to learn more about it, check out their [documentation](https://docs.spongepowered.org/5.1.0/en/plugin/internals/mixins.html).

## Contributions
We welcome contributions, but you have to follow the following rules in order for us to merge your pull request.

You can make a pull request [here](https://github.com/GlassClient/GlassClient/issues)!

### Language and Code Quality
Your code needs to be able to build, please ensure your code has little to no bugs.
You also need to use kotlin features to make coding easier and faster, so please use kotlin and pass the [Detekt](https://github.com/detekt/detekt) code quality check, use kotlin features if you can, because we will never merge terrible code.

#### Kotlin Features
If applicable please use kotlin since it is more human readable, we have provided an example below of kotlin:

Using kotlin features:
~~~kotlin
Timer().schedule(2000L) { 
    // your code
}
~~~
Not using kotlin features:
~~~kotlin
Timer().schedule(object : TimerTask() {
    override fun run() {
        // your code
    }
}, 2000L)
~~~
