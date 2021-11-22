package runstatic.stools.entity.support

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 *
 * @author chenmoand
 */
@Suppress("UNCHECKED_CAST")
abstract class NamedPair<K, V> protected constructor(
    @JsonIgnore
    val keyName: String,
    @JsonIgnore
    val valueName: String
) : BaseEntity() {

    override fun addProperty(key: String, value: Any) {
        if (keyName == key || valueName == key) {
            super.addProperty(key, value)
        }
    }

    open var key: K
        set(value) {
            properties[keyName] = value!!
        }
        @JsonIgnore
        get() = properties[keyName] as K

    open var value: V
        set(_value) {
            properties[valueName] = _value!!
        }
        @JsonIgnore
        get() = properties[keyName] as V
}