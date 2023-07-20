package gateway

import internal.entity.Account
import internal.entity.Transaction

interface TransactionGateway {
    fun create(transaction: Transaction)
}