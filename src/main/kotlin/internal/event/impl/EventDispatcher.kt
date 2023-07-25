package internal.event.impl

import internal.event.EventDispatcherInterface
import internal.event.EventHandlerInterface
import internal.event.EventInterface
import internal.event.HandlerAlreadyExistsException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EventDispatcher(
    private val handlers: MutableMap<String, MutableList<EventHandlerInterface>> = mutableMapOf()
) : EventDispatcherInterface {
    override fun register(eventName: String, handler: EventHandlerInterface) {
        if (handlers.containsKey(eventName)) {
            throw HandlerAlreadyExistsException("Handler for event $eventName already exists")
        } else {
            handlers[eventName]?.add(handler) ?: handlers.put(eventName, mutableListOf(handler))
        }
    }

    override fun dispatch(event: EventInterface) {
        runBlocking {
            handlers[event.getName()]?.let {
                coroutineScope {
                    val jobs = it.map { handler -> launch { handler.handle(event) } }
                    jobs.joinAll()
                }
            }
        }.also { println("EventInterface ${event.getName()} dispatched") }
    }

    override fun remove(eventName: String, handler: EventHandlerInterface) {
        if (handlers.containsKey(eventName)) {
            handlers[eventName]!!.remove(handler)
        }
    }

    override fun has(eventName: String, handler: EventHandlerInterface): Boolean =
        handlers.containsKey(eventName) && handlers[eventName]?.contains(handler) ?: false

    override fun clear() {
        handlers.clear()
    }

}