package org.example.eda.adapter.repository

import org.example.eda.internal.entity.Account
import org.example.eda.internal.repository.AccountRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object AccountModel : Table("accounts") {
    val id: Column<String> = varchar("id", 36)
    val balance: Column<Float> = float("balance")
    val client: Column<String> =
        reference("client_id", ClientModel.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val createdAt = date("created_at").default(LocalDate.now())
    val updatedAt = date("updated_at").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Account_ID")
}

class AccountRepositoryImpl(
    private val clientRepositoryImpl: ClientRepositoryImpl,
) : AccountRepository {

    override fun create(account: Account) {
        transaction {
            AccountModel.insert {
                it[id] = account.id
                it[balance] = account.balance
                it[client] = account.client.id
                it[createdAt] = account.createdAt
                it[updatedAt] = account.updatedAt
            }
        }
    }

    @Throws(NoSuchElementException::class)
    override fun getById(id: String): Account =
        transaction {
            AccountModel.select {
                AccountModel.id eq id
            }.first().let {
                Account.create(
                    id = it[AccountModel.id],
                    balance = it[AccountModel.balance],
                    client = clientRepositoryImpl.getById(it[AccountModel.client]),
                    createdAt = it[AccountModel.createdAt],
                    updatedAt = it[AccountModel.updatedAt],
                )
            }
        }
}
