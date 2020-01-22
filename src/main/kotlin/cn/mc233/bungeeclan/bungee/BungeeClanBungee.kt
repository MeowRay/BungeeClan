package cn.mc233.bungeeclan.bungee

import cn.mc233.bungeeclan.spigot.manager.ClanBungeeConfigManager
import cn.mc233.bungeeclan.spigot.manager.ClanBungeeDatabaseManager
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Plugin

class BungeeClanBungee : Plugin() {
    override fun onEnable() {
        ClanBungeeConfigManager.init(dataFolder)
        ClanBungeeDatabaseManager.init()
    }

    override fun onDisable() {
        ClanBungeeDatabaseManager.close()
    }
}