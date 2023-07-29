package org.example.eda.adapter.repository

import org.example.eda.domain.entity.Account
import org.example.eda.domain.entity.Client
import org.example.eda.domain.repository.AccountRepository
import org.example.eda.domain.repository.ClientRepository
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

    fun toDomain(row: ResultRow, client: Client): Account =
        Account(
            id = row[id],
            balance = row[balance],
            client = client,
            createdAt = row[createdAt],
            updatedAt = row[updatedAt],
        )
}

class AccountRepositoryImpl(
    private val clientRepository: ClientRepository,
) : AccountRepository {

    override fun create(account: Account): String =
        transaction {
            AccountModel.insert {
                it[id] = account.id
                it[balance] = account.balance
                it[client] = account.client.id
                it[createdAt] = account.createdAt
                it[updatedAt] = account.updatedAt
            } get AccountModel.id
        }

    override fun getById(id: String): Account? =
        transaction {
            AccountModel.select {
                AccountModel.id eq id
            }.firstOrNull()?.let {
                val client = clientRepository.getById(it[AccountModel.client]) ?: throw Exception("Client not found")
                AccountModel.toDomain(it, client)
            }
        }
}
