package internal.repository

import internal.entity.Account

interface AccountRepository {
    @Throws(NoSuchElementException::class)
    fun getById(id: String): Account
    fun create(account: Account)
}