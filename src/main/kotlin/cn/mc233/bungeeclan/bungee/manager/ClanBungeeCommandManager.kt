package cn.mc233.bungeeclan.bungee.manager

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Plugin

object ClanBungeeCommandManager {
    fun init(plugin:Plugin){

        plugin.proxy.pluginManager.registerCommand(plugin,object: Command(""){
            override fun execute(sender: CommandSender?, args: Array<out String>?) {
            }

        })
    }
}