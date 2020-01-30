package cn.mc233.bungeeclan.common.manager

import cn.mc233.bungeeclan.common.exception.NotFoundServiceException
import cn.mc233.bungeeclan.common.service.PlayerMapService
import cn.mc233.bungeeclan.common.service.PlayerMapServiceDef
import cn.mc233.bungeeclan.common.service.Service

object ClanServiceManager : RegistrationManager<Service>() {

    fun init() {
        this.register(PlayerMapService::class, PlayerMapServiceDef())
    }

    @Throws(NotFoundServiceException::class)
    inline fun <reified T : Service> with(block: T.() -> Unit) {
        this.withSafe(block, onNotFound = {
            throw NotFoundServiceException()
        })
    }

    @Throws(NotFoundServiceException::class)
    inline fun <reified T : Service, R> withAndResult(block: T.() -> R): R {
        return this.withAndResultSafe(block, onNotFound = {
            throw NotFoundServiceException()
        })!!
    }
}

