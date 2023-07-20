package internal.entity

import java.time.LocalDate
import java.util.*

class Transaction(
    val id: String,
    val accountFrom: Account,
    val accountTo: Account,
    val amount: Float,
    val createdAt: LocalDate = LocalDate.now(),
) {

    init {
        if (!isValid()) {
            throw IllegalArgumentException("Invalid transaction")
        }
    }

    fun commit() {
        accountFrom.debit(amount)
        accountTo.credit(amount)
    }

    private fun isValid() =
        id.isNotBlank() &&
                amount > 0 &&
                accountFrom.balance >= amount

    companion object {
        fun create(
            accountFrom: Account,
            accountTo: Account,
            amount: Float,
        ): Transaction {
            val transaction = Transaction(
                id = UUID.randomUUID().toString(),
                accountFrom = accountFrom,
                accountTo = accountTo,
                amount = amount,
            )

            if (!transaction.isValid()) {
                throw IllegalArgumentException("Invalid transaction")
            }

            transaction.commit()
            return transaction
        }
    }
}