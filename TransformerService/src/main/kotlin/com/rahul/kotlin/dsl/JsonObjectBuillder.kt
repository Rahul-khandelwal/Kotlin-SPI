package com.rahul.kotlin.dsl

import org.json.JSONObject
import java.util.*

class JsonObjectBuilder {
    private val deque: Deque<JSONObject> = ArrayDeque<JSONObject>()

    fun json(build: JsonObjectBuilder.() -> Unit): JSONObject {
        deque.push(JSONObject())
        this.build()
        return deque.pop()
    }

    infix fun <T> String.To(value: T) {
        deque.peek().put(this, value)
    }
}

fun json(build: JsonObjectBuilder.() -> Unit): JSONObject {
    return JsonObjectBuilder().json(build)
}