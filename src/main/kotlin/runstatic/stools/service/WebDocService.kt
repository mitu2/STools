package runstatic.stools.service

import java.io.InputStream

/**
 *
 * @author chenmoand
 */
interface WebDocService {

    fun getLatestVersion(type: String, group: String, artifactId: String): String

    fun getDocInputStream(type: String, group: String, artifactId: String, version: String, path: String): InputStream

}