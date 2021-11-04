package runstatic.stools.constant

/**
 *
 * @author chenmoand
 */
sealed interface ValidGroups {

    interface All : First, Second, Third, Fourth, Fifth

    interface First : ValidGroups

    interface Second : ValidGroups

    interface Third : ValidGroups

    interface Fourth : ValidGroups

    interface Fifth : ValidGroups
}



