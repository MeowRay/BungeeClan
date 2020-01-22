package cn.mc233.bungeeclan.bungee.command

import cn.mc233.bungeeclan.common.manager.ClanCommandManager
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.plugin.Command

class BungeeCommandProxy(name: String) : Command(name) {
    override fun execute(sender: CommandSender, args: Array<String>) {
        ClanCommandManager.onExecute(this.name, BungeeCommandSender(sender), args)
    }
}