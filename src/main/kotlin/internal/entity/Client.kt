package internal.entity

import java.time.LocalDate
import java.util.*

class Client private constructor(
    var id: String,
    var name: String,
    var email: String,
    var createdAt: LocalDate,
    var updatedAt: LocalDate?,
) {

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

    companion object {
        fun create(
            id: String = UUID.randomUUID().toString(),
            name: String,
            email: String,
            createdAt: LocalDate = LocalDate.now(),
            updatedAt: LocalDate? = null,
        ): Client = Client(id, name, email, createdAt, updatedAt).also { newClient ->
            newClient.isValid().also {
                if (!it) {
                    throw IllegalArgumentException("Invalid client")
                }
            }
        }
    }
}