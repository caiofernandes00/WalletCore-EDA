package org.example.eda.internal.repository

import org.example.eda.internal.entity.Client

interface ClientRepository {
    @Throws(NoSuchElementException::class)
    fun getById(id: String): Client
    fun create(client: Client)
}