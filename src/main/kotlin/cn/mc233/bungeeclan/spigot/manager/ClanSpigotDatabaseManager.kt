package cn.mc233.bungeeclan.spigot.manager

import cn.mc233.bungeeclan.common.manager.ClanDatabaseManager
import java.io.Closeable

object ClanSpigotDatabaseManager : Closeable {
    fun init() {
        ClanDatabaseManager.init()
    }

    override fun close() {
        ClanDatabaseManager.close()
    }
}