package cn.mc233.bungeeclan.common.config

data class DatabaseDefinedConfig(
        val master: Source
) {
    data class Source(
            val url: String,
            val driver: String = "com.mysql.jdbc.Driver",
            val user: String = "root",
            val password: String = "",
            val tablePrefix: String = "bungeeclan_",
            val playerIdMapTable: String=""
    )
}