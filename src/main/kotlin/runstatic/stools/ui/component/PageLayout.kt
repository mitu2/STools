package runstatic.stools.ui.component

import com.github.mvysny.karibudsl.v10.div
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.HasStyle
import com.vaadin.flow.component.Tag
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.server.AppShellSettings

/**
 *
 * @author chenmoand
 */
@Tag(Tag.DIV)
@CssImport("./css/page-layout.css")
class PageLayout(
    className: String? = null,
    initCallback: Div.() -> Unit = {}
) : Component(), HasComponents, HasStyle, AppShellConfigurator {

    private val main = div("main")

    override fun configurePage(settings: AppShellSettings) {
        settings.addFavIcon("icon", "/icon/favicon.ico", "16x16")
    }

    init {
        setId("app")
        if (className != null) {
            addClassName(className)
        }
        initCallback(main)
        add(PageFooter())
    }


}