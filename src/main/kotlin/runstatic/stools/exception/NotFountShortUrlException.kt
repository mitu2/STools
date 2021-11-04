package runstatic.stools.exception

import javassist.NotFoundException

/**
 *
 * @author chenmoand
 */
class NotFountShortUrlException(msg: String?) : NotFoundException(msg)
