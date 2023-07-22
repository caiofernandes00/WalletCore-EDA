package internal.database

import internal.entity.Client
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object ClientEntity : Table("clients") {
    val id: Column<String> = varchar("id", 36)
    val name: Column<String> = varchar("name", 255)
    val email: Column<String> = varchar("email", 255)
    val createdAt = date("created_at").default(LocalDate.now())
    val updatedAt = date("updated_at").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_Client_ID")
}

class ClientDb {
    fun save(client: Client) =
        transaction {
            ClientEntity.insert {
                it[id] = client.id
                it[name] = client.name
                it[email] = client.email
            }
        }

    @Throws(NoSuchElementException::class)
    fun getById(id: String): Client {
        return transaction {
            ClientEntity.select {
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
        }
    }
}