package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import runstatic.stools.constant.RegexpConsts
import runstatic.stools.service.ShortUrlService
import runstatic.stools.ui.component.PageFooter
import runstatic.stools.util.VaadinProp
import runstatic.stools.util.prop
import kotlin.random.Random

/**
 *
 * @author chenmoand
 */
@Route("short-url")
@PageTitle("短网址一键生成 - static.run")
@UIScope
@SpringComponent
@CssImport("./css/shortUrl.css")
class ShortUrlView(
    private val shortUrlService: ShortUrlService
) : KComposite() {


    private var urlField: TextField = TextField("网址").apply {
        isRequired = true
        placeholder = "请在此输入一个的网址"
        pattern = RegexpConsts.URL_REGEXP
        isClearButtonVisible = true
        errorMessage = "您输入的网址不符合规则"
        isAutoselect = true
        prefixComponent = FlexLayout().apply {
            @Suppress("HttpUrlsUsage")
            val protocol = Select<String>().apply {
                setItems("http://", "https://")
                value = "http://"
                addClassName("protocol")
            }
        }
    }


    private var url by urlField.prop("")


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
                add(urlField)
            }
            add(PageFooter())
        }
    }

}