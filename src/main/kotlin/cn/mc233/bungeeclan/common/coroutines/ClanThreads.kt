package cn.mc233.bungeeclan.common.coroutines

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.newFixedThreadPoolContext
import java.util.concurrent.Executors

object ClanThreads {
    val command by lazy {
        Executors.newFixedThreadPool(2).asCoroutineDispatcher()
    }
}