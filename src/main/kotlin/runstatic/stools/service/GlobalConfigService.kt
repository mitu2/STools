package runstatic.stools.service

/**
 *
 * @author chenmoand
 */
interface GlobalConfigService {

    fun getValue(key: String, _default: String? = null): String?

}