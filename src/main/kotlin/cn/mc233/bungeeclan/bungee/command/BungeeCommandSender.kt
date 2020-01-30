package cn.mc233.bungeeclan.bungee.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent

class BungeeCommandSender(val dst: CommandSender) : cn.mc233.bungeeclan.common.command.CommandSender<CommandSender> {
    override val isSpigot = false
    override val isBungeeCord = true

    override val name: String
        get() = dst.name

    override fun sendMessage(vararg messages: String) {
        dst.sendMessage(*messages.map { TextComponent(it) }.toTypedArray())
    }

    override fun hasPermission(permission: String): Boolean {
        return dst.hasPermission(permission)
    }

    override fun cast(): CommandSender {
        return dst
    }

}