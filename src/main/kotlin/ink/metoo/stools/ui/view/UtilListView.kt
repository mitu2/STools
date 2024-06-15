package ink.metoo.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import ink.metoo.stools.ui.entity.RouterState
import ink.metoo.stools.ui.entity.TextFieldState
import ink.metoo.stools.ui.stye.MeStyle.baseStyle
import ink.metoo.stools.ui.stye.inputRightStyle
import ink.metoo.stools.ui.stye.pointerStyle
import ink.metoo.stools.ui.util.pageLayout

/**
 *
 * @author chenmoand
 */
@Route("util-list")
@PageTitle("工具列表 - metoo.ink")
@CssImport("./css/util-list.css")
@SpringComponent
@UIScope
class UtilListView : KComposite() {

    private val root = ui {
        pageLayout {
            addClassName("app")
            h3("哇卡卡卡的小工具")
            p("希望能帮到你哦!")
            formLayout {
                baseStyle()
                routers.forEach { router ->
                    routerLink(null, router.label) {
                        setRoute(router.target.java, router.params)
                    }
                }
            }
        }
    }

    companion object {

        private val routers = listOf(
            RouterState("二维码", BarcodeView::class),
            RouterState("JSON格式化", JsonFormatView::class),
            RouterState("短网址", ShortUrlView::class),
            RouterState("MavenJarDoc", WebDocParseView::class),
            RouterState("变量名", VariableNameView::class),
        )

    }

}