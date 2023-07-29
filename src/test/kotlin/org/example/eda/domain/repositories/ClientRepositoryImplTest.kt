package org.example.eda.domain.repositories

import org.example.eda.domain.entity.Client
import org.example.eda.adapter.repository.ClientRepositoryImpl
import org.example.eda.adapter.repository.Config
import org.junit.jupiter.api.Test

internal class ClientRepositoryImplTest {

    @Test
    fun `should save a client`() {
        Config.connect()
        Config.migrate()

        val clientRepositoryImpl = ClientRepositoryImpl()
        val client = Client(
            name = "John Doe",
            email = "mail@mail.com",
        )
        clientRepositoryImpl.create(client)

        val clientFromDb = clientRepositoryImpl.getById(client.id)
        assert(clientFromDb!!.id == client.id)
        assert(clientFromDb.name == client.name)
        assert(clientFromDb.email == client.email)
    }
}