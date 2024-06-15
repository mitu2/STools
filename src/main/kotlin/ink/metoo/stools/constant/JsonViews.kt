package ink.metoo.stools.constant

/**
 *
 * @author chenmoand
 */
sealed interface JsonViews {

    interface All : First, Second, Third, Fourth, Fifth

    interface First : JsonViews

    interface Second : JsonViews

    interface Third : JsonViews

    interface Fourth : JsonViews

    interface Fifth : JsonViews
}

