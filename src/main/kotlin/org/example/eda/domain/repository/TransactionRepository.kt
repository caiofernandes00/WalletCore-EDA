package org.example.eda.domain.repository

import org.example.eda.domain.entity.Transaction

interface TransactionRepository {
    fun create(transaction: Transaction): String
    fun getById(id: String): Transaction?
}