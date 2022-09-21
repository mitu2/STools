package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.JsModule
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
import runstatic.stools.configuration.SToolsProperties
import runstatic.stools.service.ShortUrlService
import runstatic.stools.ui.stye.css
import runstatic.stools.ui.stye.inputRightStyle
import runstatic.stools.ui.stye.pointerStyle
import runstatic.stools.ui.util.VaadinProp
import runstatic.stools.ui.util.pageLayout

/**
 *
 * @author chenmoand
 */
@Route("short-url")
@PageTitle("短网址一键生成 - static.run")
@UIScope
@SpringComponent
@JsModule("./lib/copytoclipboard.js")
class ShortUrlView @Autowired constructor(
    private val shortUrlService: ShortUrlService,
    private val properties: SToolsProperties
) : KComposite() {

    // private val logger = useSlf4jLogger()

    companion object {
        @Suppress("HttpUrlsUsage")
        val PROTOCOLS = arrayOf("http://", "https://")

        @Suppress("HttpUrlsUsage")
        const val DEFAULT_PROTOCOL = "http://"

        private fun Select<String>.protocolStyle() = css {
            style["width"] = "105px"
            style["margin-left"] = "-5px"
        }
    }


    private val protocolSelect = Select<String>().apply {
        setItems(*PROTOCOLS)

    }

    private val hostAndPathField: TextField = TextField("网址").apply {
        isRequired = true
        placeholder = "请在此输入一个的网址"
        isClearButtonVisible = true
        isAutoselect = true
        prefixComponent = FlexLayout().apply {
            add(protocolSelect)
        }
        addValueChangeListener {
            formatHostAndPath(it.value)
        }
    }

    private val resultField: TextField = TextField("结果").apply {
        isReadOnly = true
        suffixComponent = Button("复制", VaadinIcon.COPY.create()).apply {
            inputRightStyle().pointerStyle()
            addClickListener {
                UI.getCurrent().page.executeJs("window.copyToClipboard($0)", value)
            }
        }
    }


    private var protocol by VaadinProp(DEFAULT_PROTOCOL, protocolSelect)
    private var hostAndPath by VaadinProp("", hostAndPathField)
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
                    add(hostAndPathField)
                    add(resultField)
                    button("制作") {
                        pointerStyle()
                        addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST)
                        onLeftClick {
                            makeShotUrl()
                        }
                    }
                }
            }
        }
    }

    init {
        // add select inner style
        protocolSelect.element.executeJs(
            """this.shadowRoot
                .querySelector('vaadin-select-text-field')
                .style['padding'] = '0px'
            """.trimIndent()
        )
    }

    fun formatHostAndPath(value: String?) {

        if (value.isNullOrBlank()) {
            return
        }

        for (_protocol in PROTOCOLS) {
            val letUrl = value.lowercase()
            if (letUrl.startsWith(_protocol)) {
                hostAndPath = letUrl.replaceFirst(_protocol, "")
                protocol = _protocol
                break
            }
        }

    }

    fun makeShotUrl() {
        if (hostAndPath.isNullOrBlank()) {
            Notification.show("请输入正常的链接", 3000, Notification.Position.TOP_CENTER)
            return
        }
        val httpUrl = protocol + hostAndPath
        val router = shortUrlService.randomShortUrl(httpUrl)
        result = "${properties.baseUrl}/s/$router"
    }


}