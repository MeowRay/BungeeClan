package cn.mc233.bungeeclan.common.command

interface CommandSender<T> {
    val isSpigot: Boolean
    val isBungeeCord: Boolean

    val name: String


    fun sendMessage(vararg messages: String)
    fun hasPermission(permission: String): Boolean

    fun cast(): T
}