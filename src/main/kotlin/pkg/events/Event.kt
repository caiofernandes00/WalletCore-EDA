package pkg.events

import java.util.*

class HandlerAlreadyExistsException(message: String) : Exception(message)

interface Event {
    fun getName(): String
    fun getDateTime(): Date
    fun getPayload(): Any
}

interface EventHandlerInterface {
    fun handle(event: Event)
}

interface EventDispatcherInterface {
    @Throws(HandlerAlreadyExistsException::class)
    fun register(eventName: String, handler: EventHandlerInterface)

    @Throws(Exception::class)
    fun dispatch(event: Event)

    @Throws(Exception::class)
    fun remove(eventName: String, handler: EventHandlerInterface)

    @Throws(Exception::class)
    fun has(eventName: String, handler: EventHandlerInterface): Boolean

    @Throws(Exception::class)
    fun clear()

}