package cn.mc233.bungeeclan.common.model

import org.jetbrains.exposed.dao.EntityID

object ModelClans : BaseIntIdTable(name = "clan") {

    val ownerPlayer = reference("owner_player_id", ModelPlayers)
}

class ModelClan(id: EntityID<Int>) : BaseIntEntity(id, ModelClans) {
    companion object : BaseIntEntityClass<ModelClan>(ModelClans)

    var ownerPlayer by ModelPlayer referencedOn ModelClans.ownerPlayer

}