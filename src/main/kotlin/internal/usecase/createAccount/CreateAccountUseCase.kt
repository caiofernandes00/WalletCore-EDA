package internal.usecase.createAccount

import internal.entity.Account
import internal.repository.AccountRepository
import internal.repository.ClientRepository

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