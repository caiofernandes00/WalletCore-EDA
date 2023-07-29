package org.example.eda.domain.repository

import org.example.eda.domain.entity.Client

interface ClientRepository {
    fun getById(id: String): Client?
    fun create(client: Client): String
}