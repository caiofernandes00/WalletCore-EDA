package adapter.repository

import internal.entity.Transaction
import internal.repository.TransactionRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object TransactionModel : Table("transactions") {
    val id: Column<String> = varchar("id", 36)
    val amount: Column<Float> = float("balance")
    val accountFrom: Column<String> =
        reference("account_from", AccountModel.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val accountTo: Column<String> =
        reference("account_to", AccountModel.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val createdAt = date("created_at").default(LocalDate.now())
    val updatedAt = date("updated_at").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Transaction_ID")
}

class TransactionRepositoryImpl(
    private val accountRepositoryImpl: AccountRepositoryImpl,
) : TransactionRepository {
    override fun create(transaction: Transaction) {
        transaction {
            TransactionModel.insert {
                it[id] = transaction.id
                it[amount] = transaction.amount
                it[accountFrom] = transaction.accountFrom.id
                it[accountTo] = transaction.accountTo.id
            }
        }
    }

    @Throws(NoSuchElementException::class)
    override fun getById(id: String): Transaction {
        return transaction {
            TransactionModel.select { TransactionModel.id eq id }
                .first().let {
                    Transaction(
                        id = it[TransactionModel.id],
                        accountFrom = accountRepositoryImpl.getById(it[TransactionModel.accountFrom]),
                        accountTo = accountRepositoryImpl.getById(it[TransactionModel.accountTo]),
                        amount = it[TransactionModel.amount],
                        createdAt = it[TransactionModel.createdAt],
                    )
                }
        }
    }
}