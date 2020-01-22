package cn.mc233.bungeeclan.common.repository

import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.Statement
import org.jetbrains.exposed.sql.statements.StatementType
import java.sql.PreparedStatement
import java.sql.ResultSet

open class Repository {
    fun <T : Any> Transaction.execSql(sql: String, transform: (ResultSet) -> T?): T? {
        if (sql.isEmpty()) return null

        val type = StatementType.values().find {
            sql.trim().startsWith(it.name, true)
        } ?: StatementType.OTHER

        return this.exec(object : Statement<T>(type, emptyList()) {
            override fun PreparedStatement.executeInternal(transaction: Transaction): T? {
                val result = when (type) {
                    StatementType.SELECT -> executeQuery()
                    else -> {
                        executeUpdate()
                        resultSet
                    }
                }
                return result?.let {
                    try {
                        transform(it)
                    } finally {
                        it.close()
                    }
                }
            }

            override fun prepareSQL(transaction: Transaction): String = sql

            override fun arguments(): Iterable<Iterable<Pair<ColumnType, Any?>>> = emptyList()
        })
    }
}