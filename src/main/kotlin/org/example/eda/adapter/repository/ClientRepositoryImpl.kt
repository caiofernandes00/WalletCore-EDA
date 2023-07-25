package org.example.eda.adapter.repository

import org.example.eda.internal.entity.Client
import org.example.eda.internal.repository.ClientRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object ClientModel : Table("clients") {
    val id: Column<String> = varchar("id", 36)
    val name: Column<String> = varchar("name", 255)
    val email: Column<String> = varchar("email", 255)
    val createdAt = date("created_at").default(LocalDate.now())
    val updatedAt = date("updated_at").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Client_ID")
}

class ClientRepositoryImpl : ClientRepository {
    override fun create(client: Client) {
        transaction {
            ClientModel.insert {
                it[id] = client.id
                it[name] = client.name
                it[email] = client.email
            }
        }
    }

    @Throws(NoSuchElementException::class)
    override fun getById(id: String): Client =
        transaction {
            ClientModel.select {
                ClientModel.id eq id
            }.first().let {
                Client.create(
                    id = it[ClientModel.id],
                    name = it[ClientModel.name],
                    email = it[ClientModel.email],
                    createdAt = it[ClientModel.createdAt],
                    updatedAt = it[ClientModel.updatedAt],
                )
            }
        }
}