package adapter.repository

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object Config {
    fun connect(): Database {
        return Database.connect("jdbc:h2:file:./test", driver = "org.h2.Driver", user = "root", password = "")
    }

    fun migrate() {
        transaction {
            SchemaUtils.create(ClientModel)
            SchemaUtils.create(AccountModel)
            SchemaUtils.create(TransactionModel)
        }
    }

    fun migrateDown() {
        transaction {
            SchemaUtils.drop(TransactionModel)
            SchemaUtils.drop(AccountModel)
            SchemaUtils.drop(ClientModel)
        }
    }
}
