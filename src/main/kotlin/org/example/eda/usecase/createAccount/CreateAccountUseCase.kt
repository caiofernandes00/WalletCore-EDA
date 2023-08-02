package org.example.eda.usecase.createAccount

import org.example.eda.domain.entity.Account
import org.example.eda.domain.repository.AccountRepository
import org.example.eda.domain.repository.ClientRepository

data class CreateAccountInputDTO(
    val clientId: String,
)

data class CreateAccountOutputDTO(
    val id: String,
)

class CreateAccountUseCase(
    private val accountRepository: AccountRepository,
    private val clientRepository: ClientRepository,
) {

    fun execute(input: CreateAccountInputDTO): CreateAccountOutputDTO {
        val client = clientRepository.getById(input.clientId) ?: throw Exception("Client not found")

        val account = Account(
            client = client,
            balance = 0f,
        )

        accountRepository.create(account)

        return CreateAccountOutputDTO(
            id = account.id,
        )
    }
}