package ink.metoo.stools.ui.entity

data class MavenConfig(
    var repository: String,
    val groupId: String,
    val artifactId: String,
    val versions: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MavenConfig

        if (repository != other.repository) return false
        if (groupId != other.groupId) return false
        if (artifactId != other.artifactId) return false
        if (!versions.contentEquals(other.versions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = repository.hashCode()
        result = 31 * result + groupId.hashCode()
        result = 31 * result + artifactId.hashCode()
        result = 31 * result + versions.contentHashCode()
        return result
    }
}