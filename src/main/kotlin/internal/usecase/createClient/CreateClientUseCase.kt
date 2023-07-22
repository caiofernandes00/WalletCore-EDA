package internal.usecase.createClient

import internal.entity.Client
import internal.gateway.ClientGateway
import java.time.LocalDate

data class CreateClientInputDTO(
    val name: String,
    val email: String,
)

data class CreateClientOutputDTO(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate? = null,
)

class CreateClientUseCase private constructor(
    private val clientGateway: ClientGateway,
) {

    fun execute(input: CreateClientInputDTO): CreateClientOutputDTO {
        val client = Client.create(
            name = input.name,
            email = input.email,
        )

        clientGateway.save(client)

        return CreateClientOutputDTO(
            id = client.id,
            name = client.name,
            email = client.email,
            createdAt = client.createdAt,
            updatedAt = client.updatedAt,
        )
    }

    companion object {
        fun create(clientGateway: ClientGateway): CreateClientUseCase {
            return CreateClientUseCase(clientGateway)
        }
    }
}