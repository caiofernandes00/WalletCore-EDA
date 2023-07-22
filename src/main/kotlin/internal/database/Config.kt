package internal.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Config {
    fun connect(): Database {
        return Database.connect("jdbc:h2:file:./test", driver = "org.h2.Driver", user = "root", password = "")
    }

    fun migrate() {
        transaction {
            SchemaUtils.create(ClientEntity)
            SchemaUtils.create(AccountEntity)
        }
    }

    fun migrateDown() {
        transaction {
            SchemaUtils.drop(AccountEntity)
            SchemaUtils.drop(ClientEntity)
        }
    }
}
