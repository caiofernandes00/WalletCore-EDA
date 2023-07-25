package internal.usecase.createTransaction

import internal.entity.Transaction
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import internal.event.EventDispatcherInterface
import internal.event.EventInterface
import internal.repository.AccountRepository
import internal.repository.TransactionRepository

data class CreateTransactionInputDTO(
    val accountFromId: String,
    val accountToId: String,
    val amount: Float
)

data class CreateTransactionOutputDTO(
    val id: String
)

class CreateTransactionUseCase private constructor(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val eventDispatcher: EventDispatcherInterface,
    private val transactionCreated: EventInterface
) {

    suspend fun execute(inputDTO: CreateTransactionInputDTO): CreateTransactionOutputDTO = coroutineScope {
        val accountFromDeferred = async { accountRepository.getById(inputDTO.accountFromId) }
        val accountToDeferred = async { accountRepository.getById(inputDTO.accountToId) }

        val accountFrom = accountFromDeferred.await()
        val accountTo = accountToDeferred.await()

        val transaction = Transaction.create(
            accountFrom = accountFrom,
            accountTo = accountTo,
            amount = inputDTO.amount,
        )

        transactionRepository.create(transaction)
        transactionCreated.setPayload(transaction)
        eventDispatcher.dispatch(transactionCreated)

        return@coroutineScope CreateTransactionOutputDTO(transaction.id)
    }

    companion object {
        fun create(
            transactionRepository: TransactionRepository,
            accountRepository: AccountRepository,
            eventDispatcher: EventDispatcherInterface,
            transactionCreated: EventInterface
        ): CreateTransactionUseCase {
            return CreateTransactionUseCase(transactionRepository, accountRepository, eventDispatcher, transactionCreated)
        }
    }
}