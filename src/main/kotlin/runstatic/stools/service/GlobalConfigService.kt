package runstatic.stools.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import runstatic.stools.entity.table.GlobalConfigTable

/**
 *
 * @author chenmoand
 */
interface GlobalConfigService {

    fun getEntityByKey(key: String): GlobalConfigTable?

    fun getEntityById(id: Int): GlobalConfigTable?

    fun getValue(key: String, defaultValue: String? = null): String?

    fun setValue(key: String, value: String?)


    val mapper: ObjectMapper

}

operator fun GlobalConfigService.set(key: String, value: String?) = setValue(key, value)

operator fun GlobalConfigService.set(key: String, value: Any?) {
    if(value == null) {
        setValue(key, null)
    }
    setValue(key, mapper.writeValueAsString(value))
}

inline operator fun <reified T : Any> GlobalConfigService.get(key: String): T? {
    val clz = T::class.java
    val value = getValue(key) ?: return null
    if (clz == String::class.java) {
        return value as T
    }
    return mapper.readValue(value, clz)
}

operator fun <T : Any> GlobalConfigService.get(pair: Pair<String, TypeReference<T>>): T? {
    val (key, typeRef) = pair
    val value = getValue(key) ?: return null
    return mapper.readValue(value, typeRef)

}