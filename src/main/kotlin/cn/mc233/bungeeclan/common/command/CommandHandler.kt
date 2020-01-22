package cn.mc233.bungeeclan.common.command


abstract class CommandHandler<T> {
    fun Array<String>.get(index: Int, default: String = ""): String {
        return if (this.size <= index) default else this[index]
    }

    fun Array<String>.getInt(index: Int, default: Int? = null): Int? {
        return if (this.size <= index) default else this[index].toIntOrNull()
    }

    abstract suspend fun execute(sender: CommandSender<T>, args: Array<String>)
}