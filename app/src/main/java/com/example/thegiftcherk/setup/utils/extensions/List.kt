package com.example.thegiftcherk.setup.utils.extensions

fun <T, R> List<T>.intersect(list: List<R>, filterPredicate: (T, R) -> Boolean): List<T> =
    filter { t -> list.any { r -> filterPredicate(t, r) } }

fun <T, R> List<T>.difference(list: List<R>, filterPredicate: (T, R) -> Boolean): List<T> =
    filterNot { t -> list.any { r -> filterPredicate(t, r) } }