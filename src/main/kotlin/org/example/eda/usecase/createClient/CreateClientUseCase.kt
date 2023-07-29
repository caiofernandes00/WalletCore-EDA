package org.example.eda.usecase.createClient

import org.example.eda.domain.entity.Client
import org.example.eda.domain.repository.ClientRepository
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

class CreateClientUseCase(
    private val clientRepository: ClientRepository,
) {

    fun execute(input: CreateClientInputDTO): CreateClientOutputDTO {
        val client = Client(
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

}