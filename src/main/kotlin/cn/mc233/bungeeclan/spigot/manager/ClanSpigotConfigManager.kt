package cn.mc233.bungeeclan.spigot.manager

import cn.mc233.bungeeclan.common.manager.ClanConfigManager
import java.io.File

object ClanSpigotConfigManager {
    fun init(baseDir: File) {
        ClanConfigManager.init(baseDir)
    }
}