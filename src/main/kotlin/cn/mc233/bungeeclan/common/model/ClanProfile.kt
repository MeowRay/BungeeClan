package cn.mc233.bungeeclan.common.model

import org.jetbrains.exposed.dao.EntityID


object ModelClanProfiles : BaseIntIdTable(name = "clan_profile") {
    val clan = reference("clan_id", ModelClans)
    val name = varchar("name", 32)
}

class ModelClanProfile(id: EntityID<Int>) : BaseIntEntity(id, ModelClanProfiles) {
    companion object : BaseIntEntityClass<ModelClanProfile>(ModelClanProfiles)

    var clan by ModelClan referencedOn ModelClanProfiles.clan
    var name by ModelClanProfiles.name
}