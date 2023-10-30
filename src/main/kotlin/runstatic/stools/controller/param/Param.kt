package runstatic.stools.controller.param

import org.springframework.beans.BeanUtils

/**
 *
 * @author chenmoand
 */
interface Param {

    fun <T : Any> Param.copyFieldValues(target: T, vararg ignoreProperties: String = emptyArray()): T {
        BeanUtils.copyProperties(this, target, *ignoreProperties)
        return target
    }

}

