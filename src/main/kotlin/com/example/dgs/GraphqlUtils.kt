package com.example.dgs

typealias ID = String

data class FieldInitializeException(val msg: String) : RuntimeException(msg)

fun throwFieldInitException(field: String): () -> Nothing = { throw FieldInitializeException("$field 값이 설정되지 않았습니다") }

fun (() -> Any).isInitField(): Boolean {
    return try {
        this.invoke()
        true
    } catch (e: FieldInitializeException) {
        false
    }
}