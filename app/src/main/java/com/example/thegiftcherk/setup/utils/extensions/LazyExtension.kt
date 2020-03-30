package com.example.thegiftcherk.setup.utils.extensions

fun <T> lazyUnsychronized(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)