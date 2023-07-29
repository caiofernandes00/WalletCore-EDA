package org.example.eda.domain.entity

import org.example.eda.domain.entity.Account
import org.example.eda.domain.entity.Client
import org.example.eda.domain.entity.Transaction
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TransactionTest {

    @Test
    fun `create transaction`() {
        val transaction = Transaction(
            accountFrom = accountFrom,
            accountTo = accountTo,
            amount = 10f,
        )

        assert(transaction.id.isNotBlank())
        assert(transaction.accountFrom.balance == 90f)
        assert(transaction.accountTo.balance == 110f)
    }

    @Test
    fun `create transaction with invalid amount`() {
        val e = assertThrows<IllegalArgumentException> {
            Transaction(
                accountFrom = accountFrom,
                accountTo = accountTo,
                amount = -10f,
            )
        }

        assert(e.message == "Invalid transaction")
    }


    companion object {
        private val client = Client(
            name = "John Doe",
            email = "d@d.com",
        )

        private val accountFrom = Account(
            client = client,
            balance = 100f,
        )

        private val accountTo = Account(
            client = client,
            balance = 100f,
        )
    }
}