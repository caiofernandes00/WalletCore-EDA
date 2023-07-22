package internal.database

import internal.entity.Account
import internal.entity.Client
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object AccountEntity : Table("accounts") {
    val id: Column<String> = varchar("id", 36)
    val balance: Column<Float> = float("balance")
    val client: Column<String> =
        reference("client_id", ClientEntity.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val createdAt = date("created_at").default(LocalDate.now())
    val updatedAt = date("updated_at").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Account_ID")
}

class AccountDb {
    fun save(account: Account) =
        transaction {
            AccountEntity.insert {
                it[id] = account.id
                it[balance] = account.balance
                it[client] = account.client.id
                it[createdAt] = account.createdAt
                it[updatedAt] = account.updatedAt
            }
        }

    @Throws(NoSuchElementException::class)
    fun getById(id: String): Account {
        return transaction {
            val client = ClientEntity.select {
                ClientEntity.id eq id
            }.first().let {
                Client.create(
                    id = it[ClientEntity.id],
                    name = it[ClientEntity.name],
                    email = it[ClientEntity.email],
                    createdAt = it[ClientEntity.createdAt],
                    updatedAt = it[ClientEntity.updatedAt],
                )
            }

            AccountEntity.select {
                AccountEntity.id eq id
            }.first().let {
                Account.create(
                    id = it[AccountEntity.id],
                    balance = it[AccountEntity.balance],
                    client = client,
                    createdAt = it[AccountEntity.createdAt],
                    updatedAt = it[AccountEntity.updatedAt],
                )
            }
        }
    }
}