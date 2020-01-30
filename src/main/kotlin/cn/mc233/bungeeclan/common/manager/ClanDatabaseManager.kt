package cn.mc233.bungeeclan.common.manager

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.io.Closeable

object ClanDatabaseManager : Closeable {

    lateinit var master: Database

    fun init() {
        val config = ClanConfigManager.database
        master = Database.connect(
                url = config.master.url,
                driver = config.master.driver,
                user = config.master.user,
                password = config.master.password
        )
    }

    override fun close() {
        if (this::master.isInitialized) {
            TransactionManager.closeAndUnregister(this.master)
        }
    }
}