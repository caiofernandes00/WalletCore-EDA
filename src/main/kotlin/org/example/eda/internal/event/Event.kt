package org.example.eda.internal.event

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
    @Throws(HandlerAlreadyExistsException::class)
    fun register(eventName: String, handler: EventHandlerInterface)

    @Throws(Exception::class)
    fun dispatch(event: EventInterface)

    @Throws(Exception::class)
    fun remove(eventName: String, handler: EventHandlerInterface)

    @Throws(Exception::class)
    fun has(eventName: String, handler: EventHandlerInterface): Boolean

    @Throws(Exception::class)
    fun clear()

}