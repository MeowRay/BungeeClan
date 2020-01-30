package cn.mc233.bungeeclan.common.model

import org.jetbrains.exposed.dao.EntityID

object ModelClans : BaseIntIdTable(name = "clan") {

    val ownerPlayer = integer("owner_player_id")
}

class ModelClan(id: EntityID<Int>) : BaseIntEntity(id, ModelClans) {
    companion object : BaseIntEntityClass<ModelClan>(ModelClans)

    var ownerPlayer by ModelClans.ownerPlayer

}