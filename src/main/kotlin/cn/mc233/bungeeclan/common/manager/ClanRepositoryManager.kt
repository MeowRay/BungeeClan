package cn.mc233.bungeeclan.common.manager

import cn.mc233.bungeeclan.common.exception.NotFoundRepositoryException
import cn.mc233.bungeeclan.common.repository.ClanRepository
import cn.mc233.bungeeclan.common.repository.PlayerRepository
import cn.mc233.bungeeclan.common.repository.Repository

object ClanRepositoryManager : RegistrationManager<Repository>() {
    fun init() {
        this.register(
                PlayerRepository(),
                ClanRepository()
        )
    }

    @Throws(NotFoundRepositoryException::class)
    inline fun <reified T : Repository> with(block: T.() -> Unit) {
        this.withSafe(block, onNotFound = {
            throw NotFoundRepositoryException()
        })
    }

    @Throws(NotFoundRepositoryException::class)
    inline fun <reified T : Repository, R> withAndResult(block: T.() -> R): R {
        return this.withAndResultSafe(block, onNotFound = {
            throw NotFoundRepositoryException()
        })!!
    }
}

