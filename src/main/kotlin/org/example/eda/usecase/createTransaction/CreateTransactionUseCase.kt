package org.example.eda.usecase.createTransaction

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.example.eda.domain.entity.Transaction
import org.example.eda.domain.event.EventDispatcherInterface
import org.example.eda.domain.event.EventInterface
import org.example.eda.domain.repository.AccountRepository
import org.example.eda.domain.repository.TransactionRepository

data class CreateTransactionInputDTO(
    val accountFromId: String,
    val accountToId: String,
    val amount: Float
)

data class CreateTransactionOutputDTO(
    val id: String
)

class CreateTransactionUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val eventDispatcher: EventDispatcherInterface,
    private val event: EventInterface
) {

    suspend fun execute(inputDTO: CreateTransactionInputDTO): CreateTransactionOutputDTO = coroutineScope {
        val accountFromDeferred = async { accountRepository.getById(inputDTO.accountFromId) }
        val accountToDeferred = async { accountRepository.getById(inputDTO.accountToId) }

        val accountFrom = accountFromDeferred.await() ?: throw Exception("Account from not found")
        val accountTo = accountToDeferred.await() ?: throw Exception("Account to not found")

        val transaction = Transaction(
            accountFrom = accountFrom,
            accountTo = accountTo,
            amount = inputDTO.amount,
        )

        transactionRepository.create(transaction)

        event.setPayload(transaction)
        eventDispatcher.dispatch(event)

        return@coroutineScope CreateTransactionOutputDTO(transaction.id)
    }
}