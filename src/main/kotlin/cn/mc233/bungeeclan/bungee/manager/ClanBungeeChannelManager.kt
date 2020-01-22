package cn.mc233.bungeeclan.bungee.manager

import cn.mc233.bungeeclan.bungee.annotation.ChannelMethodInvokeHandler
import cn.mc233.bungeeclan.bungee.utils.isChannelMethodInvoke
import cn.mc233.bungeeclan.bungee.utils.isChannelMethodResult
import cn.mc233.bungeeclan.common.utils.*
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteStreams
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.event.EventHandler
import java.io.Closeable
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.memberFunctions

typealias ChannelTimeoutCallback = () -> Unit

typealias ChannelResultCallback = (data: ByteArrayDataInput) -> Unit

class ChannelCallbackResultTimeout(val call: ChannelResultCallback, timeout: Long = 3000L, val timeoutCall: ChannelTimeoutCallback = {}) {
    val timeoutIn = System.currentTimeMillis() + timeout
}

internal object ClanBungeeChannelManager : Listener, Closeable {
    fun init(plugin: Plugin) {
        plugin.proxy.registerChannel(CLAN_CHANNEL_NAME_METHOD)
        plugin.proxy.registerChannel(CLAN_CHANNEL_NAME_METHOD_RESULT)

        plugin.proxy.pluginManager.registerListener(plugin, this)
    }

    // <方法名,<监听器,监听器方法>>
    private val registeredChannelMethodInvokeListeners = HashMap<String, ArrayList<Pair<Any, KFunction<*>>>>()
    private val registeredChannelMethodResultCallbacks = HashMap<Long, ChannelCallbackResultTimeout>()

    private var channelMethodResultSessionCount = 0L
    @Synchronized
    fun generateChannelResultSessionId(): Long {
        return channelMethodResultSessionCount++
    }

    fun registerChannelMethodInvokeListener(listener: Any) {
        listener::class.memberFunctions.forEach {
            if (it.findAnnotation<ChannelMethodInvokeHandler>() != null) {
                if (registeredChannelMethodInvokeListeners.containsKey(it.name)) {
                    registeredChannelMethodInvokeListeners[it.name]!!.add(listener to it)
                } else {
                    registeredChannelMethodInvokeListeners[it.name] = arrayListOf(listener to it)
                }
            }
        }
    }

    fun registerChannelMethodResultCallback(sessionId: Long, callback: ChannelResultCallback, timeout: Long = 3000L, timeoutCall: ChannelTimeoutCallback = {}) {
        if (this.registeredChannelMethodResultCallbacks.size % 10 == 0) {
            val now = System.currentTimeMillis()
            this.registeredChannelMethodResultCallbacks.toMap().forEach { (k, v) ->
                if (now > v.timeoutIn) {
                    this.registeredChannelMethodResultCallbacks.remove(k)
                    v.timeoutCall()
                }
            }
        }
        registeredChannelMethodResultCallbacks[sessionId] = ChannelCallbackResultTimeout(callback, timeout, timeoutCall)
    }

    @EventHandler
    fun onChannelMessage(event: PluginMessageEvent) {
        if (event.sender is ProxiedPlayer) {
            return
        }
        if (event.isChannelMethodResult) {
            event.isCancelled = true
            val input = ByteStreams.newDataInput(event.data)
            val sessionId = input.readLong()
            val callback = registeredChannelMethodResultCallbacks[sessionId] ?: return
            registeredChannelMethodResultCallbacks.remove(sessionId)
            callback.call(input)
        } else if (event.isChannelMethodInvoke) {
            event.isCancelled = true
            val data = ByteStreams.newDataInput(event.data).readMethodChannelData()
            val paramRawMap = data.args[0].toObjectFromJson(HashMap::class)
            this.registeredChannelMethodInvokeListeners[data.methodName]!!.forEach { item ->
                val args = hashMapOf<KParameter, Any?>(item.second.instanceParameter!! to item.first)
                item.second.parameters.forEach { param ->
                    args[param] = paramRawMap[param.name]
                }
                item.second.callBy(args)
            }

        }
    }

    override fun close() {
        this.channelMethodResultSessionCount = 0
        this.registeredChannelMethodInvokeListeners.clear()
        this.registeredChannelMethodResultCallbacks.clear()
    }


}