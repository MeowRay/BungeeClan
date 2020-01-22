package cn.mc233.bungeeclan.common.manager

import cn.mc233.bungeeclan.common.config.DatabaseDefinedConfig
import com.uchuhimo.konf.Config
import com.uchuhimo.konf.source.yaml
import com.uchuhimo.konf.toValue
import java.io.File
import java.io.InputStream

object ClanConfigManager {
    lateinit var database: DatabaseDefinedConfig

    fun init(baseDir: File) {
        this.database = loadConfig(baseDir.resolve("database.yml"))
    }

    fun initByText(database: String) {
        this.database = loadConfig(database)
    }

    fun initByInputStream(database: InputStream) {
        this.database = loadConfig(database)
    }


    private inline fun <reified T> loadConfig(text: String): T {
        return Config().from.yaml.string(text).toValue()
    }

    private inline fun <reified T> loadConfig(input: InputStream): T {
        return Config().from.yaml.inputStream(input).toValue()
    }
    private inline fun <reified T> loadConfig(file: File): T {
        return Config().from.yaml.file(file).toValue()
    }


}