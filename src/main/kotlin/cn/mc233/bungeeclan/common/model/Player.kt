package cn.mc233.bungeeclan.common.model

import cn.mc233.bungeeclan.common.manager.ClanConfigManager
import org.jetbrains.exposed.dao.EntityID
import java.util.*


object ModelPlayers : BaseIntIdTable() {
    override val tableName: String
        get() = ClanConfigManager.database.master.playerIdMapTable

    val name = varchar("player", 16).uniqueIndex()
    val uuid = varchar("uuid", 36).uniqueIndex()
}

class ModelPlayer(id: EntityID<Int>) : BaseIntEntity(id, ModelPlayers) {
    companion object : BaseIntEntityClass<ModelPlayer>(ModelPlayers)

    var name by ModelPlayers.name
    var uuid: UUID by ModelPlayers.uuid.transform({ it.toString() }, { UUID.fromString(it) })
}