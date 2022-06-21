package runstatic.stools.constant

/**
 *
 * @author chenmoand
 */
object RegexpCosts {

    const val ACCOUNT_REGEXP = "^[a-zA-Z0-9_-]{5,20}\$"
    const val PASSWORD_REGEXP = "^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#\$%^&*? ]).*"
    const val URL_REGEXP =
        "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?"

}