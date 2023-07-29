package org.example.eda.domain.entity

import java.time.LocalDate
import java.util.*

class Client(
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var email: String,
    var createdAt: LocalDate = LocalDate.now(),
    var updatedAt: LocalDate? = null,
) {

    init {
        if (!isValid()) {
            throw IllegalArgumentException("Invalid client")
        }
    }

    fun update(name: String, email: String) {
        this.name = name
        this.email = email
        this.updatedAt = LocalDate.now()

        if (!isValid()) {
            throw IllegalArgumentException("Invalid client")
        }
    }

    private fun isValid() =
        id.isNotBlank() && name.isNotBlank() && email.isNotBlank()
}