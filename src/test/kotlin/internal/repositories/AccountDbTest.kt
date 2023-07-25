package internal.repositories

import org.example.eda.adapter.repository.AccountRepositoryImpl
import org.example.eda.internal.entity.Account
import org.example.eda.internal.entity.Client
import org.example.eda.adapter.repository.ClientRepositoryImpl
import org.example.eda.adapter.repository.Config
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
        val clientRepositoryImpl = ClientRepositoryImpl()
        val accountRepositoryImpl = AccountRepositoryImpl(clientRepositoryImpl)
        val client = Client.create(
            name = "John Doe",
            email = "mail@mail.com",
        )
        val account = Account.create(
            balance = 100.0f,
            client = client,
        )
        clientRepositoryImpl.create(client)
        accountRepositoryImpl.create(account)

        val accountResult = accountRepositoryImpl.getById(account.id)
        assert(accountResult.id == account.id)
        assert(accountResult.balance == account.balance)
        assert(accountResult.client.id == account.client.id)
    }
}