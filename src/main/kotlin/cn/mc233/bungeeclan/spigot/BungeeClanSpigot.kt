package cn.mc233.bungeeclan.spigot

import cn.mc233.bungeeclan.spigot.manager.ClanBungeeConfigManager
import cn.mc233.bungeeclan.spigot.manager.ClanBungeeDatabaseManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class BungeeClanSpigot : JavaPlugin() {
    override fun onEnable() {
        ClanBungeeConfigManager.init(dataFolder)
        ClanBungeeDatabaseManager.init()
    }

    override fun onDisable() {
        ClanBungeeDatabaseManager.close()
    }

    override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?): Boolean {
        return super.onCommand(sender, command, label, args)
    }
}