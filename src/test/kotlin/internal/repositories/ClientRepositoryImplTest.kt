package internal.repositories

import internal.entity.Client
import adapter.repository.ClientRepositoryImpl
import adapter.repository.Config
import org.junit.jupiter.api.Test

internal class ClientRepositoryImplTest {

    @Test
    fun `should save a client`() {
        Config.connect()
        Config.migrate()

        val clientRepositoryImpl = ClientRepositoryImpl()
        val client = Client.create(
            name = "John Doe",
            email = "mail@mail.com",
        )
        clientRepositoryImpl.create(client)

        val clientFromDb = clientRepositoryImpl.getById(client.id)
        assert(clientFromDb.id == client.id)
        assert(clientFromDb.name == client.name)
        assert(clientFromDb.email == client.email)
    }
}