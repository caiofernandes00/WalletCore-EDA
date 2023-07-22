package internal.database

import internal.entity.Account
import internal.entity.Client
import internal.entity.Transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class TransactionDbTest {

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
    fun `should save a transaction`() {
        val clientDb = ClientDb()
        val accountDb = AccountDb(clientDb)
        val transactionDb = TransactionDb(accountDb)
        val client = Client.create(
            name = "John Doe",
            email = "mail@mail.com",
        )
        val account = Account.create(
            balance = 100.0f,
            client = client,
        )
        val transaction = Transaction.create(
            accountFrom = account,
            accountTo = account,
            amount = 100.0f,
        )
        clientDb.save(client)
        accountDb.save(account)
        transactionDb.save(transaction)

        val transactionResult = transactionDb.getById(transaction.id)
        assert(transactionResult.accountFrom.id == transaction.accountFrom.id)
        assert(transactionResult.accountTo.id == transaction.accountTo.id)
        assert(transactionResult.amount == transaction.amount)
    }
}