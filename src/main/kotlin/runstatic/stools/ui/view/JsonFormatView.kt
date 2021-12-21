package runstatic.stools.ui.view

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.flexLayout
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import runstatic.stools.ui.component.PageFooter
import runstatic.stools.util.VaadinProp
import runstatic.stools.util.pageLayout

/**
 *
 * @author chenmoand
 */
@Route("json-format-util")
@PageTitle("Json格式化工具 - static.run")
@CssImport("./css/json-format-util.css")
@SpringComponent
@UIScope
class JsonFormatView @Autowired constructor(
    mapperBuilder: Jackson2ObjectMapperBuilder
) : KComposite() {

    private val mapper: ObjectMapper = mapperBuilder.createXmlMapper(false).build()

    private val inJsonTextArea = TextArea("inJson").apply {
        addClassName("json-text-area")
        isRequired = true
        minHeight = "300px"
        maxHeight = "300px"
    }
    private var inJson: String by VaadinProp("{\n}", inJsonTextArea)

    private val outJsonTextArea = TextArea("outJson").apply {
        addClassName("json-text-area")
        isReadOnly = true
        minHeight = "300px"
        maxHeight = "300px"
    }
    private var outJson: String by VaadinProp("", outJsonTextArea)


    private val root = ui {
        pageLayout {
            flexLayout {
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                flexWrap = FlexLayout.FlexWrap.WRAP
                add(inJsonTextArea)
                add(outJsonTextArea)
                // formLayout {
                //
                // }
            }
        }
    }

}