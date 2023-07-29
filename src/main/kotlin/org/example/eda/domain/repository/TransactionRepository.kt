package org.example.eda.internal.repository

import org.example.eda.internal.entity.Transaction

interface TransactionRepository {
    fun create(transaction: Transaction)
    @Throws(NoSuchElementException::class)
    fun getById(id: String): Transaction
}