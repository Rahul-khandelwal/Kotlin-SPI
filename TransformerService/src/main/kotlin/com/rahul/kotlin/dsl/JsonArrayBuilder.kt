package com.rahul.kotlin.dsl

import org.json.JSONArray
import java.util.*

class JsonArrayBuilder {
    private val deque: Deque<JSONArray> = ArrayDeque<JSONArray>()

    fun jsonArray(build: JsonArrayBuilder.() -> Unit): JSONArray {
        deque.push(JSONArray())
        this.build()
        return deque.pop()
    }

    infix operator fun plus(value: Any) {
        deque.peek().put(value)
    }
}

fun jsonArray(build: JsonArrayBuilder.() -> Unit): JSONArray {
    return JsonArrayBuilder().jsonArray(build)
}