package org.example.eda.domain.repository

import org.example.eda.domain.entity.Account

interface AccountRepository {
    fun getById(id: String): Account?
    fun create(account: Account): String
}