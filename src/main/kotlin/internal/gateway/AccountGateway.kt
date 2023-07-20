package internal.gateway

import internal.entity.Account

interface AccountGateway {
    fun getById(id: String): Account?
    fun save(account: Account)
}