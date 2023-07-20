package usecase.createClient

import gateway.ClientGateway
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class CreateClientUseCaseTest {

    private val clientGateway = mockk<ClientGateway>()

    @Test
    fun `create a client`() {
        // Given
        every { clientGateway.save(any()) } returns Unit
        val useCase = CreateClientUseCase.create(clientGateway)

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