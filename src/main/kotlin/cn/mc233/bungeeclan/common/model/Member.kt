package cn.mc233.bungeeclan.common.model

import org.jetbrains.exposed.dao.EntityID


object ModelMembers : BaseIntIdTable(name = "clan_member") {
    val clan = reference("clan_id", ModelClans).index()
    val player = integer("player_id").index()
}

class ModelMember(id: EntityID<Int>) : BaseIntEntity(id, ModelMembers) {
    companion object : BaseIntEntityClass<ModelMember>(ModelMembers)

    var clan by ModelClan referencedOn ModelMembers.clan
    var player by ModelPlayer referencedOn ModelMembers.player
}