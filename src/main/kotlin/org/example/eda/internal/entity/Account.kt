package org.example.eda.internal.entity

import java.time.LocalDate
import java.util.*

class Account private constructor(
    val id: String,
    var client: Client,
    var balance: Float,
    val createdAt: LocalDate,
    var updatedAt: LocalDate?,
) {

    fun update(balance: Float) {
        this.balance = balance
        this.updatedAt = LocalDate.now()

        if (!isValid()) {
            throw IllegalArgumentException("Invalid account")
        }
    }

    fun addAccount(client: Client) {
        if (this.client.id != client.id) {
            throw IllegalArgumentException("Invalid client")
        }
        this.client = client
    }

    fun debit(amount: Float) {
        if (amount > balance) {
            throw IllegalArgumentException("Invalid amount")
        }
        this.balance -= amount
    }

    fun credit(amount: Float) {
        this.balance += amount
    }

    private fun isValid() =
        id.isNotBlank() && balance >= 0

    companion object {
        fun create(
            id: String = UUID.randomUUID().toString(),
            client: Client,
            balance: Float,
            createdAt: LocalDate = LocalDate.now(),
            updatedAt: LocalDate? = null,
        ): Account {
            return Account(id, client, balance, createdAt, updatedAt).also { newAccount ->
                newAccount.isValid().also {
                    if (!it) {
                        throw IllegalArgumentException("Invalid account")
                    }
                }
            }
        }
    }
}