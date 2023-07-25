package usecase.createClient

import internal.repository.ClientRepository
import internal.usecase.createClient.CreateClientInputDTO
import internal.usecase.createClient.CreateClientUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class CreateClientUseCaseTest {

    private val clientRepository = mockk<ClientRepository>()

    @Test
    fun `create a client`() {
        // Given
        every { clientRepository.create(any()) } returns Unit
        val useCase = CreateClientUseCase.create(clientRepository)

        // When
        val output = useCase.execute(createClientInputDTO)

        // Then
        assert(output.name == "John Doe")
        assert(output.email == "d@d.com")
    }

    companion object {
        val createClientInputDTO = CreateClientInputDTO(
            name = "John Doe",
            email = "d@d.com",
        )
    }
}