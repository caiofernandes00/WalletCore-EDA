package org.example.eda.domain.event.impl

import org.example.eda.domain.event.EventInterface
import java.util.*

class TransactionCreated(
    private val name: String,
    private var payload: Any
) : EventInterface {

    override fun getName(): String {
        return name
    }

    override fun getPayload(): Any {
        return payload
    }

    override fun setPayload(payload: Any) {
        this.payload = payload
    }

    override fun getDateTime(): Date {
        return Date()
    }
}