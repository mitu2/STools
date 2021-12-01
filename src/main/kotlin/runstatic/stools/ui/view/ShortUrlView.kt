package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.flexLayout
import com.github.mvysny.karibudsl.v10.formLayout
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.material.Material
import runstatic.stools.constant.RegexpConsts
import runstatic.stools.service.ShortUrlService
import runstatic.stools.ui.component.PageFooter
import kotlin.random.Random

/**
 *
 * @author chenmoand
 */
@Route("short-url")
@PageTitle("短网址一键生成 - static.run")
@UIScope
@SpringComponent
//@Theme(value = Material::class)
class ShortUrlView(
    private val shortUrlService: ShortUrlService
) : KComposite() {


    private val random = Random

    private var url = ""

    private val root = ui {
        flexLayout {
            flexWrap = FlexLayout.FlexWrap.WRAP
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            setFlexDirection(FlexLayout.FlexDirection.COLUMN)
            alignContent = FlexLayout.ContentAlignment.CENTER
            alignItems = FlexComponent.Alignment.BASELINE
            formLayout {
                width = "400px"
                style["margin"] = "0 auto"
                textField("网址") {
                    value = url
                    isRequired = true
                    placeholder = "请输入一个正常的网址网址"
                    pattern = RegexpConsts.URL_REGEXP
                    addValueChangeListener { url = it.value }
                }
            }
            add(PageFooter())
        }
    }


}