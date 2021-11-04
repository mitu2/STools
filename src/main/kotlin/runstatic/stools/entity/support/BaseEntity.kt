package runstatic.stools.entity.support

import com.fasterxml.jackson.annotation.JsonAnyGetter
import java.io.Serializable

/**
 *
 * @author chenmoand
 */
abstract class BaseEntity: Serializable {

    val properties: MutableMap<String, Any>
        by lazy(LazyThreadSafetyMode.PUBLICATION) { initProperty() }
        @JsonAnyGetter get

    protected open fun initProperty(): MutableMap<String, Any> = hashMapOf();



    open fun addProperty(key:String, value: Any) {
        this.properties[key] = value
    }

}