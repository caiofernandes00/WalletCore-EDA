package org.example.eda.domain.event.impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.eda.domain.event.EventDispatcherInterface
import org.example.eda.domain.event.EventHandlerInterface
import org.example.eda.domain.event.EventInterface

class EventDispatcher(
    private val handlers: MutableMap<String, MutableSet<EventHandlerInterface>> = mutableMapOf()
) : EventDispatcherInterface {
    override fun register(eventName: String, handler: EventHandlerInterface) {
        handlers[eventName]?.add(handler) ?: handlers.put(eventName, mutableSetOf(handler))
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