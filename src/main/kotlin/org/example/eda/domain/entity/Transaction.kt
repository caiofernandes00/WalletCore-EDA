package org.example.eda.domain.entity

import java.time.LocalDate
import java.util.*

class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val accountFrom: Account,
    val accountTo: Account,
    val amount: Float,
    val createdAt: LocalDate = LocalDate.now(),
) {

    init {
        if (!isValid()) {
            throw IllegalArgumentException("Invalid transaction")
        }

        commit()
    }

    private fun commit() {
        accountFrom.debit(amount)
        accountTo.credit(amount)
    }

    private fun isValid() =
        id.isNotBlank() &&
                amount > 0 &&
                accountFrom.balance >= amount
}