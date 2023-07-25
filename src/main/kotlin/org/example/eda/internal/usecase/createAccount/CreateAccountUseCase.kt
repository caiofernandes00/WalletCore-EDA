package org.example.eda.internal.usecase.createAccount

import org.example.eda.internal.entity.Account
import org.example.eda.internal.repository.AccountRepository
import org.example.eda.internal.repository.ClientRepository

data class CreateAccountInputDTO(
    val clientId: String,
)

data class CreateAccountOutputDTO(
    val id: String,
)

class CreateAccountUseCase private constructor(
    private val accountRepository: AccountRepository,
    private val clientRepository: ClientRepository,
) {

    fun execute(input: CreateAccountInputDTO): CreateAccountOutputDTO {
        val client = clientRepository.getById(input.clientId)
            ?: throw IllegalArgumentException("Invalid client")

        val account = Account.create(
            client = client,
            balance = 0f,
        )

        accountRepository.create(account)

        return CreateAccountOutputDTO(
            id = account.id,
        )
    }

    companion object {
        fun create(accountRepository: AccountRepository, clientRepository: ClientRepository): CreateAccountUseCase {
            return CreateAccountUseCase(accountRepository, clientRepository)
        }
    }
}