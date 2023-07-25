package internal.repository

import internal.entity.Transaction

interface TransactionRepository {
    fun create(transaction: Transaction)
    @Throws(NoSuchElementException::class)
    fun getById(id: String): Transaction
}