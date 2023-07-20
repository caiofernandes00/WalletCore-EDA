package usecase.createTransaction

import gateway.AccountGateway
import gateway.TransactionGateway
import internal.entity.Transaction
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

data class CreateTransactionInputDTO(
    val accountFromId: String,
    val accountToId: String,
    val amount: Float
)

data class CreateTransactionOutputDTO(
    val id: String
)

class CreateTransactionUseCase private constructor(
    private val transactionGateway: TransactionGateway,
    private val accountGateway: AccountGateway
) {

    suspend fun execute(inputDTO: CreateTransactionInputDTO): CreateTransactionOutputDTO = coroutineScope {
        val accountFromDeferred = async { accountGateway.getById(inputDTO.accountFromId) }
        val accountToDeferred = async { accountGateway.getById(inputDTO.accountToId) }

        val accountFrom = accountFromDeferred.await()
        val accountTo = accountToDeferred.await()

        if (accountFrom == null || accountTo == null) {
            throw IllegalArgumentException("Invalid account")
        }

        val transaction = Transaction.create(
            accountFrom = accountFrom,
            accountTo = accountTo,
            amount = inputDTO.amount,
        )

        transactionGateway.create(transaction)

        return@coroutineScope CreateTransactionOutputDTO(transaction.id)
    }

    companion object {
        fun create(transactionGateway: TransactionGateway, accountGateway: AccountGateway): CreateTransactionUseCase {
            return CreateTransactionUseCase(transactionGateway, accountGateway)
        }
    }
}