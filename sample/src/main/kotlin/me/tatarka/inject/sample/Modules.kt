package me.tatarka.inject.sample

import me.tatarka.inject.annotations.*

@Inject
@Singleton
class Foo() : IFoo

interface IFoo

class Bar()

@Inject
class Baz(foo: IFoo, bar: Bar)

@Module
@Singleton
abstract class MyModule {
    abstract val baz: Baz

    @get:Binds
    protected abstract val Foo.binds: IFoo

    @Provides
    @Singleton
    protected fun bar() = Bar()
}

fun main() {
    val module: MyModule = MyModule::class.create()
    println(module.baz)
}

