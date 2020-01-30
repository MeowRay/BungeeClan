package common

import cn.mc233.bungeeclan.common.manager.ClanConfigManager
import cn.mc233.bungeeclan.common.manager.ClanDatabaseManager
import cn.mc233.bungeeclan.common.model.*
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ClanDaoTest : StringSpec() {

    private fun <T> testTransaction(db: Database? = null, statement: Transaction.() -> T): T {
        return transaction(db = db) {
            addLogger(StdOutSqlLogger)
            val tables = arrayOf(ModelClans, ModelClanProfiles, ModelMembers, ModelPlayers)
            SchemaUtils.drop(*tables)
            SchemaUtils.createMissingTablesAndColumns(*tables)
            this.statement()
        }
    }

    init {
        ClanConfigManager.initByInputStream(
                database = ClassLoader.getSystemResourceAsStream("database.yml")!!
        )
        ClanDatabaseManager.init()
        "clan new".config {
            testTransaction {
                val player = ModelPlayer.new {
                    this.name = "test"
                    this.uuid = UUID.randomUUID()
                }
                val gang = ModelClan.new {
                    this.ownerPlayer = player.id.value
                }
                ModelClan.find { ModelClans.id eq gang.id }.single().let {
                    it.ownerPlayer shouldBe player.id.value
                }
            }
        }
        "clan profile new".config {
            testTransaction {
                val player = ModelPlayer.new {
                    this.name = "test"
                    this.uuid = UUID.randomUUID()
                }
                val clan = ModelClan.new {
                    this.ownerPlayer = player.id.value
                }
                ModelClanProfile.new {
                    this.clan = clan
                    this.name = "测试工会"
                }
                ModelClanProfile.find { ModelClanProfiles.clan eq clan.id }.single().let {
                    it.clan shouldBe clan
                    it.name shouldBe "测试工会"
                    it.clan.ownerPlayer shouldBe player.id.value
                }

            }
        }
        "clan member add".config {
            testTransaction {
                val ownerPlayer = ModelPlayer.new {
                    this.name = "test"
                    this.uuid = UUID.randomUUID()
                }
                val clan = ModelClan.new {
                    this.ownerPlayer = ownerPlayer.id.value
                }

                for (i in 0..10) {
                    val player = ModelPlayer.new {
                        this.name = "member$i"
                        this.uuid = UUID.randomUUID()
                    }
                    ModelMember.new {
                        this.clan = clan
                        this.player = player.id.value
                    }
                }
                ModelMember.find { ModelMembers.clan eq clan.id }.forEachIndexed { i, modelMember ->
                    ModelPlayer.findById(modelMember.player)!!.name shouldBe "member$i"
                }
            }


        }
    }
}