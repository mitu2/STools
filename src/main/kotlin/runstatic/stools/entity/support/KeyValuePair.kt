package runstatic.stools.entity.support

/**
 *
 * @author chenmoand
 */
class KeyValuePair<K, V>(key: K, value: V) : NamedPair<K, V>("key", "value") {
    init {
        super.key = key
        super.value = value
    }
}
