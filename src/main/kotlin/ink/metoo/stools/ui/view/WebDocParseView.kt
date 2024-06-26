package ink.metoo.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import ink.metoo.stools.configuration.SToolsProperties
import ink.metoo.stools.logging.useSlf4jLogger
import ink.metoo.stools.service.WebDocService
import ink.metoo.stools.ui.entity.MavenConfig
import ink.metoo.stools.ui.stye.inputRightStyle
import ink.metoo.stools.ui.stye.pointerStyle
import ink.metoo.stools.ui.util.VaadinProp
import ink.metoo.stools.ui.util.pageLayout
import java.util.*

/**
 *
 * @author chenmoand
 */
@Route("web-doc-parse")
@PageTitle("web-doc url生成工具 - metoo.ink")
@SpringComponent
@UIScope
class WebDocParseView @Autowired constructor(
    private val properties: SToolsProperties,
    private val webDocService: WebDocService,
) : KComposite() {

    private val repositoryKeys = LinkedList(properties.webDocResources.keys)

    private val repositorySelect = Select<String>().apply {
        setItems(repositoryKeys)
        label = "仓库"
    }

    private val groupIdInput = TextField("group").apply {
        isRequired = true
        placeholder = "please input groupId"
        isClearButtonVisible = true
        isAutoselect = true
    }

    private val artifactIdInput = TextField("artifactId").apply {
        isRequired = true
        placeholder = "please input artifactId"
        isClearButtonVisible = true
        isAutoselect = true
    }

    private val versionSelect = Select<String>().apply {
        label = "version"
        isReadOnly = true
        pointerStyle()
        setItems(mutableListOf(LATEST_VERSION))
        addFocusListener {
            onFocusVersion()
        }
    }

    @Volatile
    private var cacheMavenConfig: MavenConfig? = null


    private val resultField: TextField = TextField("地址").apply {
        isReadOnly = true
        suffixComponent = Button("打开", VaadinIcon.OPTION.create()).apply {
            pointerStyle()
            inputRightStyle()
            addClickListener {
                if (value.isNotBlank()) {
                    UI.getCurrent().page.open(value)
                }
            }
        }
    }


    private var repository by VaadinProp(repositoryKeys[0], repositorySelect)
    private var groupId by VaadinProp("", groupIdInput)
    private var artifactId by VaadinProp("", artifactIdInput)
    private var version by VaadinProp(LATEST_VERSION, versionSelect)
    private var result by VaadinProp("", resultField)


    private val root = ui {
        pageLayout {
            flexLayout {
                flexWrap = FlexLayout.FlexWrap.WRAP
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                setFlexDirection(FlexLayout.FlexDirection.COLUMN)
                alignContent = FlexLayout.ContentAlignment.CENTER
                alignItems = FlexComponent.Alignment.BASELINE
                formLayout {
                    width = "400px"
                    style["margin"] = "0 auto"
                    add(repositorySelect)
                    add(groupIdInput)
                    add(artifactIdInput)
                    add(versionSelect)
                    add(resultField)
                    button("生成") {
                        pointerStyle()
                        addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                        onLeftClick {
                            makeWebDocUrl()
                        }
                    }
                }
            }
        }
    }

    private fun makeWebDocUrl() {
        var letResult = "${properties.baseUrl}/web-doc/${repository}/${groupId}:${artifactId}"
        if (version != LATEST_VERSION) {
            letResult += ":$version"
        }
        result = letResult
    }


    private fun onFocusVersion() {
        val letCacheMavenConfig = cacheMavenConfig

        if (letCacheMavenConfig != null) {
            if ((letCacheMavenConfig.repository == repository) && (letCacheMavenConfig.groupId == groupId) && (letCacheMavenConfig.artifactId == artifactId)) {
                return
            }
        }

        if (groupId.isNotBlank() && artifactId.isNotBlank()) {
            try {
                val versions = webDocService.getMavenMetaData(repository, groupId, artifactId)
                    .select("metadata > versioning > versions > version")
                    .map {
                        it.html()
                    }.toTypedArray()
                cacheMavenConfig = MavenConfig(repository, groupId, artifactId, versions)
                if (versions.isNotEmpty()) {
                    versionSelect.setItems(LATEST_VERSION, *versions.reversedArray())
                    version = LATEST_VERSION
                    versionSelect.isReadOnly = false
                }

            } catch (e: Exception) {
                Notification.show(
                    "获得版本列表异常, 请重试或者检查groupId和artifactId",
                    3000,
                    Notification.Position.TOP_CENTER
                )
                logger.debug(e.message, e)
            }
        } else {
            versionSelect.setItems(mutableListOf(LATEST_VERSION))
            version = LATEST_VERSION
            versionSelect.isReadOnly = true
            cacheMavenConfig = null
            Notification.show("请先输入groupId和artifactId", 3000, Notification.Position.TOP_CENTER)
        }
    }

    companion object {
        const val LATEST_VERSION = "latest"
        private val logger = WebDocParseView.useSlf4jLogger()
    }


}