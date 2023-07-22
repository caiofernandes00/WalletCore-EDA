package internal.database

import internal.entity.Client
import org.junit.jupiter.api.Test

internal class ClientDbTest {

    @Test
    fun `should save client`() {
        Config.connect()
        Config.migrate()

        val clientDb = ClientDb()
        val client = Client(
            name = "John Doe",
            email = "mail@mail.com",
        )
        clientDb.save(client)

        val clientFromDb = clientDb.getById(client.id)
        assert(clientFromDb.id == client.id)
        assert(clientFromDb.name == client.name)
        assert(clientFromDb.email == client.email)
    }
}