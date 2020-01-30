package cn.mc233.bungeeclan.common.repository

import cn.mc233.bungeeclan.common.manager.ClanRepositoryManager
import cn.mc233.bungeeclan.common.model.ModelPlayer
import cn.mc233.bungeeclan.common.model.ModelPlayers
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

val ClanRepositoryManager.player: PlayerRepository
    get() = get(PlayerRepository::class)

class PlayerRepository() : Repository() {


    fun getPlayerById(id: Int): ModelPlayer? = transaction {
        ModelPlayer.find { ModelPlayers.id eq id }.firstOrNull()
    }

    fun getPlayerByName(name: String): ModelPlayer? = transaction {
        ModelPlayer.find { ModelPlayers.name eq name }.firstOrNull()
    }


    fun getPlayerByUUID(uuid: String): ModelPlayer? = transaction {
        ModelPlayer.find { ModelPlayers.uuid eq uuid }.firstOrNull()
    }

    fun getPlayerByUUID(uuid: UUID): ModelPlayer? = transaction {
        this@PlayerRepository.getPlayerByUUID(uuid.toString())
    }
}