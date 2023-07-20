package internal.entity

import java.util.*

class Account private constructor(
    val id: String = UUID.randomUUID().toString(),
    var client: Client,
    var balance: Float,
    val createdAt: Date = Date(),
    var updatedAt: Date? = null,
) {

    fun update(balance: Float) {
        this.balance = balance
        this.updatedAt = Date()

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
            client: Client,
            balance: Float,
        ): Account {
            return Account(client = client, balance = balance).also { newAccount ->
                newAccount.isValid().also {
                    if (!it) {
                        throw IllegalArgumentException("Invalid account")
                    }
                }
            }
        }
    }
}