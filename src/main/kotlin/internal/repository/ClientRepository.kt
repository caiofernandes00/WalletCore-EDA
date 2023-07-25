package internal.repository

import internal.entity.Client

interface ClientRepository {
    @Throws(NoSuchElementException::class)
    fun getById(id: String): Client
    fun create(client: Client)
}