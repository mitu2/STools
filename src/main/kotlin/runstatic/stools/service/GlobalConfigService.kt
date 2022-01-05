package runstatic.stools.service

/**
 *
 * @author chenmoand
 */
interface GlobalConfigService {

    fun getValue(key: String, defaultValue: String? = null): String?

    fun setValue(key: String, value: String?)

    operator fun set(key: String, value: String) = setValue(key, value)

    operator fun get(key: String) = getValue(key)
}