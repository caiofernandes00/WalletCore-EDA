package internal.entity

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

    companion object {
        fun create(
            name: String,
            email: String,
        ): Client {
            return Client(name = name, email = email).also { newClient ->
                newClient.isValid().also {
                    if (!it) {
                        throw IllegalArgumentException("Invalid client")
                    }
                }
            }
        }
    }
}