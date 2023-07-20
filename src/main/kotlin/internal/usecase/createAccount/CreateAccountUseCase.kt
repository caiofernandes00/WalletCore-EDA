package internal.usecase.createAccount

import internal.gateway.AccountGateway
import internal.gateway.ClientGateway
import internal.entity.Account

data class CreateAccountInputDTO(
    val clientId: String,
)

data class CreateAccountOutputDTO(
    val id: String,
)

class CreateAccountUseCase private constructor(
    private val accountGateway: AccountGateway,
    private val clientGateway: ClientGateway,
) {

    fun execute(input: CreateAccountInputDTO): CreateAccountOutputDTO {
        val client = clientGateway.getById(input.clientId)
            ?: throw IllegalArgumentException("Invalid client")

        val account = Account.create(
            client = client,
            balance = 0f,
        )

        accountGateway.save(account)

        return CreateAccountOutputDTO(
            id = account.id,
        )
    }

    companion object {
        fun create(accountGateway: AccountGateway, clientGateway: ClientGateway): CreateAccountUseCase {
            return CreateAccountUseCase(accountGateway, clientGateway)
        }
    }
}