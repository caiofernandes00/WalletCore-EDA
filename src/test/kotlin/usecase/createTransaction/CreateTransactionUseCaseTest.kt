package usecase.createTransaction

import internal.gateway.AccountGateway
import internal.gateway.TransactionGateway
import internal.entity.Account
import internal.entity.Client
import internal.usecase.createTransaction.CreateTransactionInputDTO
import internal.usecase.createTransaction.CreateTransactionUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class CreateTransactionUseCaseTest {

    private val transactionGateway = mockk<TransactionGateway>()
    private val accountGateway = mockk<AccountGateway>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `create transaction`() = runTest {
        every { transactionGateway.create(any()) } returns Unit
        every { accountGateway.getById(any()) } returns account1 andThen account2

        val useCase = CreateTransactionUseCase.create(transactionGateway, accountGateway)
        val outputDTO = useCase.execute(inputDTO)

        assert(outputDTO.id.isNotBlank())
    }

    companion object {
        val client = Client.create(
            name = "name",
            email = "mail@mail.com"
        )

        val account1 = Account.create(
            client = client,
            balance = 100f
        )

        val account2 = Account.create(
            client = client,
            balance = 100f
        )

        val inputDTO = CreateTransactionInputDTO(
            accountFromId = account1.id,
            accountToId = account2.id,
            amount = 100f
        )
    }
}