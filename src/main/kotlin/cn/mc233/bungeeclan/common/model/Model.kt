package cn.mc233.bungeeclan.common.model

import cn.mc233.bungeeclan.common.manager.ClanConfigManager
import org.jetbrains.exposed.dao.*
import org.joda.time.DateTime

abstract class BaseIntIdTable(name: String = "", columnName: String = "id") : IntIdTable(name, columnName) {

    override val tableName: String
        get() = ClanConfigManager.database.master.tablePrefix + super.tableName

    val createdAt = datetime("createdAt").default(DateTime.now())
    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()

}

abstract class BaseIntEntity(id: EntityID<Int>, table: BaseIntIdTable) : IntEntity(id) {
    val createdAt by table.createdAt
    var updatedAt by table.updatedAt
    var deletedAt by table.deletedAt

    override fun delete() {
        deletedAt = DateTime.now()
    }
}

abstract class BaseIntEntityClass<E : BaseIntEntity>(table: BaseIntIdTable) : IntEntityClass<E>(table) {

    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated)
                action.toEntity(this)?.updatedAt = DateTime.now()
        }
    }
}