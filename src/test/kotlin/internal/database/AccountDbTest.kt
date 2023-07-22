package internal.database

import internal.entity.Account
import internal.entity.Client
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class AccountDbTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            Config.connect()
            Config.migrate()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            Config.migrateDown()
        }
    }

    @Test
    fun `should save an account`() {
        val accountDb = AccountDb()
        val clientDb = ClientDb()
        val client = Client.create(
            name = "John Doe",
            email = "mail@mail.com",
        )
        val account = Account.create(
            balance = 100.0f,
            client = client,
        )
        clientDb.save(client)
        accountDb.save(account)

        val accountFromDb = accountDb.getById(account.id)
        assert(accountFromDb.id == account.id)
        assert(accountFromDb.balance == account.balance)
        assert(accountFromDb.client.id == account.client.id)
    }
}