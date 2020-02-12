package es.vanadis.utg_estaxi_profesional.setup.utils.extensions

fun <T> lazyUnsychronized(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)