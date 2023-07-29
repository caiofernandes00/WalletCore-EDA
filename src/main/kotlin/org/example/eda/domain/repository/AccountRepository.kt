package org.example.eda.internal.repository

import org.example.eda.internal.entity.Account

interface AccountRepository {
    @Throws(NoSuchElementException::class)
    fun getById(id: String): Account
    fun create(account: Account)
}