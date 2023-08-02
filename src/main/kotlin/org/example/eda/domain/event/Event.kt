package org.example.eda.domain.event

import java.util.*

class HandlerAlreadyExistsException(message: String) : Exception(message)

interface EventInterface {
    fun getName(): String
    fun getDateTime(): Date
    fun getPayload(): Any
    fun setPayload(payload: Any)
}

interface EventHandlerInterface {
    fun handle(event: EventInterface)
}

interface EventDispatcherInterface {
    fun register(eventName: String, handler: EventHandlerInterface)
    fun dispatch(event: EventInterface)
    fun remove(eventName: String, handler: EventHandlerInterface)
    fun has(eventName: String, handler: EventHandlerInterface): Boolean
    fun clear()
}
