package org.example.eda.adapter.repository

import org.example.eda.domain.entity.Account
import org.example.eda.domain.entity.Transaction
import org.example.eda.domain.repository.TransactionRepository
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

    fun toDomain(row: ResultRow, accountFrom: Account, accountTo: Account): Transaction =
        Transaction(
            id = row[id],
            amount = row[amount],
            accountFrom = accountFrom,
            accountTo = accountTo,
            createdAt = row[createdAt],
        )
}

class TransactionRepositoryImpl(
    private val accountRepositoryImpl: AccountRepositoryImpl,
) : TransactionRepository {
    override fun create(transaction: Transaction): String =
        transaction {
            TransactionModel.insert {
                it[id] = transaction.id
                it[amount] = transaction.amount
                it[accountFrom] = transaction.accountFrom.id
                it[accountTo] = transaction.accountTo.id
            } get TransactionModel.id
        }

    override fun getById(id: String): Transaction? {
        return transaction {
            TransactionModel.select { TransactionModel.id eq id }
                .firstOrNull()?.let {
                    val accountFrom = accountRepositoryImpl.getById(it[TransactionModel.accountFrom])
                        ?: throw Exception("Account not found")
                    val accountTo = accountRepositoryImpl.getById(it[TransactionModel.accountTo])
                        ?: throw Exception("Account not found")

                    TransactionModel.toDomain(it, accountFrom, accountTo)
                }
        }
    }
}