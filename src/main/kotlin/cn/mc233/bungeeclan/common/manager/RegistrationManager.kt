package cn.mc233.bungeeclan.common.manager

open class RegistrationManager<P : Any> {
    private val lockItems by lazy { Any() }

    val registeredMap = hashMapOf<Class<out P>, P>()

    fun <T : P> register(vararg list: T) {
        list.forEach {
            this.register(it.javaClass, it)
        }
    }


    fun <T : P> register(clazz: Class<T>, item: T) = synchronized(lockItems) {
        this.unregister(clazz)
        this.registeredMap[clazz] = item
    }

    fun <T : P> unregister(clazz: Class<T>) = synchronized(lockItems) {
        this.registeredMap.remove(clazz)
    }

    fun <T : P> get(clazz: Class<T>): T? {
        return this.registeredMap[clazz] as? T
    }


    inline fun <reified T : P> withSafe(block: T.() -> Unit, onNotFound: () -> Unit = {}) {
        ((this.registeredMap[T::class.java] as? T) ?: return onNotFound()).block()
    }

    inline fun <reified T : P, R> withAndResultSafe(block: T.() -> R, onNotFound: () -> R? = { null }): R? {
        return ((this.registeredMap[T::class.java] as? T) ?: return onNotFound()).block()
    }

}