package cn.mc233.bungeeclan.common.repository

import cn.mc233.bungeeclan.common.model.*
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ClanRepository : Repository() {
    /**
     * 根据 公会ID[clanId] 获取工会描述
     * 如果不存在，返回null
     */
    fun getProfileByClanId(clanId: Int): ModelClanProfile? = transaction {
        ModelClanProfile.find { ModelClanProfiles.clan eq clanId }.singleOrNull()
    }

    /**
     * 根据 公会ID[clanId] 获取工会成员列表
     */
    fun getMemberAllByClanId(clanId: Int): List<ModelMember> = transaction {
        ModelMember.find { ModelMembers.clan eq clanId }.toList()
    }

    /**
     * 根据 公会ID[clanId] 判断 玩家ID[ownerPlayerId] 是否为该工会的会长
     */
    fun hasOwnerByClanId(clanId: Int, ownerPlayerId: Int): Boolean = transaction {
        ModelClans.select {
            (ModelClans.id eq clanId) and (ModelClans.ownerPlayer eq ownerPlayerId)
        }.count() > 0
    }

    /**
     * 根据 工会ID[clanId] 获取会长
     * 如果不存在，返回null
     */
    fun getOwnerPlayerByClanId(clanId: Int): ModelPlayer? = transaction {
        ModelClan.find { ModelClans.id eq clanId }.firstOrNull()?.ownerPlayer
    }
}