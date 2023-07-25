package org.example.eda.internal.usecase.createClient

import org.example.eda.internal.entity.Client
import org.example.eda.internal.repository.ClientRepository
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
    private val clientRepository: ClientRepository,
) {

    fun execute(input: CreateClientInputDTO): CreateClientOutputDTO {
        val client = Client.create(
            name = input.name,
            email = input.email,
        )

        clientRepository.create(client)

        return CreateClientOutputDTO(
            id = client.id,
            name = client.name,
            email = client.email,
            createdAt = client.createdAt,
            updatedAt = client.updatedAt,
        )
    }

    companion object {
        fun create(clientRepository: ClientRepository): CreateClientUseCase {
            return CreateClientUseCase(clientRepository)
        }
    }
}