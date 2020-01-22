package cn.mc233.bungeeclan.spigot.manager

import cn.mc233.bungeeclan.common.manager.ClanConfigManager
import cn.mc233.bungeeclan.common.manager.ClanServiceManager
import cn.mc233.bungeeclan.common.service.TestService
import java.io.File

object ClanSpigotConfigManager {
    fun init(baseDir: File) {
        ClanConfigManager.init(baseDir)
    }
}