package cn.mc233.bungeeclan.common.manager

import cn.mc233.bungeeclan.common.command.CommandHandler
import cn.mc233.bungeeclan.common.command.CommandSender
import cn.mc233.bungeeclan.common.coroutines.ClanThreads
import kotlinx.coroutines.*


object ClanCommandManager {
    private val commands = hashMapOf<String, CommandHandler<*>>()

    fun <T> onExecute(name: String, sender: CommandSender<T>, args: Array<String>) {
        val command = (this.commands[name.toLowerCase()] as? CommandHandler<T>) ?: return
        GlobalScope.launch(ClanThreads.command) {
            command.execute(sender, args)
        }
    }
}