package cn.mc233.bungeeclan.common.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput
import kotlin.reflect.KClass

val CLAN_CHANNEL_NAME_METHOD = "CLAN_CHANNEL_METHOD"
val CLAN_CHANNEL_NAME_METHOD_RESULT = "CLAN_CHANNEL_METHOD_RESULT"
private val CLAN_OBJECT_MAPPER_CHANNEL_DATA by lazy {
    ObjectMapper()
}

fun Any.toJsonAsBytes(): ByteArray {
    return CLAN_OBJECT_MAPPER_CHANNEL_DATA.writeValueAsBytes(this)
}

fun <T : Any> ByteArray.toObjectFromJson(): T {
    return CLAN_OBJECT_MAPPER_CHANNEL_DATA.readValue(this, object : TypeReference<T>() {})
}

fun <T : Any> ByteArray.toObjectFromJson(clazz: Class<T>): T {
    return CLAN_OBJECT_MAPPER_CHANNEL_DATA.readValue(this, clazz)
}

fun <T : Any> ByteArray.toObjectFromJson(kClass: KClass<T>): T {
    return this.toObjectFromJson(kClass.java)
}

fun ByteArrayDataOutput.writeByteArray(byteArray: ByteArray) {
    this.writeInt(byteArray.size)
    this.write(byteArray)
}

fun ByteArrayDataInput.readByteArray(): ByteArray {
    return ByteArray(this.readInt()) {
        this.readByte()
    }
}

fun ByteArrayDataOutput.writeChannelMethodData(sessionId: Long, methodName: String, vararg args: Any) {
    this.writeLong(sessionId)
    this.writeUTF(methodName)
    this.writeInt(args.size)
    args.forEach { arg ->
        val data = arg.toJsonAsBytes()
        this.writeInt(data.size)
        this.write(data)
    }
}

fun ByteArrayDataInput.readMethodChannelData(): ChannelMethodData {
    return ChannelMethodData(this.readLong(), this.readUTF(),
            Array(this.readInt()) { ByteArray(this.readInt()) { this.readByte() } })
}

fun <T : Any> ByteArrayDataInput.readMethodChannelResult(): T {
    return this.readByteArray().toObjectFromJson()
}

fun <T : Any> ByteArrayDataInput.readMethodChannelResult(clazz: Class<T>): T {
    return this.readByteArray().toObjectFromJson(clazz)
}

fun <T : Any> ByteArrayDataInput.readMethodChannelResult(kClass: KClass<T>): T {
    return this.readByteArray().toObjectFromJson(kClass)
}

