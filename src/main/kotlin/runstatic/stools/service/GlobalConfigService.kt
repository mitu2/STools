package runstatic.stools.service

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

    operator fun set(key: String, value: String) = setValue(key, value)

    operator fun get(key: String) = getValue(key)
}