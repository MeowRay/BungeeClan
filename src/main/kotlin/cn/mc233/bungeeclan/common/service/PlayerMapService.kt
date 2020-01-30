package cn.mc233.bungeeclan.common.service

import cn.mc233.bungeeclan.common.manager.ClanRepositoryManager
import cn.mc233.bungeeclan.common.manager.ClanServiceManager
import cn.mc233.bungeeclan.common.model.ModelPlayer
import cn.mc233.bungeeclan.common.repository.player
import java.util.*

interface PlayerMapService : Service {
    fun getPlayerById(id: Int): ModelPlayer?
    fun getPlayerByName(name: String): ModelPlayer?
    fun getPlayerByUUID(uuid: UUID): ModelPlayer?
    fun getPlayerByUUID(uuid: String): ModelPlayer?
}

val ClanServiceManager.playerMap: PlayerMapService
    get() = get(PlayerMapService::class)

open class PlayerMapServiceDef : PlayerMapService {
    override fun getPlayerById(id: Int): ModelPlayer? = ClanRepositoryManager.player.getPlayerById(id)

    override fun getPlayerByName(name: String): ModelPlayer? = ClanRepositoryManager.player.getPlayerByName(name)

    override fun getPlayerByUUID(uuid: UUID): ModelPlayer? = this.getPlayerByUUID(uuid.toString())

    override fun getPlayerByUUID(uuid: String): ModelPlayer? = ClanRepositoryManager.player.getPlayerByUUID(uuid)

}