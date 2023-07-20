package internal.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils

object Config {
    fun connect(): Database {
        return Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
    }

    fun migrate() {
        SchemaUtils.create(ClientEntity)
    }
}
