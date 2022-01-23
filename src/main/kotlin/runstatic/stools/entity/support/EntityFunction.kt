package runstatic.stools.entity.support

import org.springframework.beans.BeanUtils
import java.io.Serializable

/**
 *
 * @author chenmoand
 */

fun <T : BaseTable<Any>> BaseEntity.copyToTable(target: T, vararg ignoreProperties: String = emptyArray()): T {
    BeanUtils.copyProperties(this, target, *ignoreProperties)
    return target
}

fun <T : BaseEntity> BaseTable<out Serializable>.copyToEntity(
    target: T,
    vararg ignoreProperties: String = emptyArray()
): T {
    BeanUtils.copyProperties(this, target, *ignoreProperties)
    return target
}

