package cn.mc233.bungeeclan.bungee.utils

import cn.mc233.bungeeclan.bungee.manager.ClanBungeeChannelManager
import cn.mc233.bungeeclan.common.utils.*
import com.google.common.io.ByteStreams
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.PluginMessageEvent
import java.util.concurrent.TimeoutException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


fun ProxiedPlayer.invokeMethod(methodName: String, vararg args: Any) {
    val output = ByteStreams.newDataOutput()
    output.writeChannelMethodData(ClanBungeeChannelManager.generateChannelResultSessionId(), methodName, *args)
    this.sendData(CLAN_CHANNEL_NAME_METHOD, output.toByteArray())
}

@Throws(TimeoutException::class)
suspend fun <R : Any> ProxiedPlayer.invokeMethodAndResult(methodName: String, vararg args: Any) = suspendCoroutine<R> { coroutine ->

    val output = ByteStreams.newDataOutput()
    val sessionId = ClanBungeeChannelManager.generateChannelResultSessionId()
    output.writeChannelMethodData(sessionId, methodName, *args)
    ClanBungeeChannelManager.registerChannelMethodResultCallback(sessionId, callback = { input ->
        coroutine.resume(input.readMethodChannelResult())
    }, timeoutCall = {
        coroutine.resumeWithException(TimeoutException())
    })
    this.sendData(CLAN_CHANNEL_NAME_METHOD, output.toByteArray())
}

val PluginMessageEvent.isChannelMethodResult: Boolean
    get() = this.tag == CLAN_CHANNEL_NAME_METHOD_RESULT
val PluginMessageEvent.isChannelMethodInvoke: Boolean
    get() = this.tag == CLAN_CHANNEL_NAME_METHOD