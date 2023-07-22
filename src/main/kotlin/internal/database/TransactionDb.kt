package internal.database

import internal.entity.Transaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object TransactionEntity : Table("transactions") {
    val id: Column<String> = varchar("id", 36)
    val amount: Column<Float> = float("balance")
    val accountFrom: Column<String> =
        reference("account_from", AccountEntity.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val accountTo: Column<String> =
        reference("account_to", AccountEntity.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val createdAt = date("created_at").default(LocalDate.now())
    val updatedAt = date("updated_at").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Transaction_ID")
}

class TransactionDb(
    private val accountDb: AccountDb,
) {
    fun save(account: Transaction) =
        transaction {
            TransactionEntity.insert {
                it[id] = account.id
                it[amount] = account.amount
                it[accountFrom] = account.accountFrom.id
                it[accountTo] = account.accountTo.id
            }
        }

    @Throws(NoSuchElementException::class)
    fun getById(id: String): Transaction {
        return transaction {
            TransactionEntity.select { TransactionEntity.id eq id }
                .first().let {
                    Transaction(
                        id = it[TransactionEntity.id],
                        accountFrom = accountDb.getById(it[TransactionEntity.accountFrom]),
                        accountTo = accountDb.getById(it[TransactionEntity.accountTo]),
                        amount = it[TransactionEntity.amount],
                        createdAt = it[TransactionEntity.createdAt],
                    )
                }
        }
    }
}