package ink.metoo.stools.service

import org.jsoup.nodes.Document
import org.springframework.core.io.Resource
import java.io.InputStream

/**
 *
 *
 * @author chenmoand
 */
interface WebDocService {

    fun getLatestVersion(type: String, group: String, artifactId: String): String

    fun getDocInputStream(type: String, group: String, artifactId: String, version: String, path: String): InputStream

    fun getDocResource(type: String, group: String, artifactId: String, version: String, path: String): Resource

    fun getMavenMetaData(type: String, group: String, artifactId: String): Document
}