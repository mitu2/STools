package runstatic.stools.ui.view

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.github.mvysny.karibudsl.v10.*
import com.github.mvysny.kaributools.selectAll
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.textfield.TextArea
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import runstatic.stools.logging.debug
import runstatic.stools.logging.useSlf4jLogger
import runstatic.stools.ui.stye.pointerStyle
import runstatic.stools.ui.util.VaadinProp
import runstatic.stools.ui.util.pageLayout

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

    private val logger = useSlf4jLogger()

    private val mapper: ObjectMapper = mapperBuilder
        .createXmlMapper(false)
        .postConfigurer {
            it.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        }
        .build()

    private val inJsonTextArea = TextArea("inJson").apply {
        addClassName("json-text-area")
        isRequired = true
        minHeight = "300px"
        maxHeight = "300px"
        maxLength = Int.MAX_VALUE
    }
    private var inJson: String by VaadinProp("{}", inJsonTextArea)

    private val outJsonTextArea = TextArea("outJson").apply {
        addClassName("json-text-area")
        isReadOnly = true
        minHeight = "300px"
        maxHeight = "300px"
        maxLength = Int.MAX_VALUE
        addFocusListener { selectAll() }
    }
    private var outJson: String by VaadinProp("", outJsonTextArea)


    private val jsonKeyOptionField = TextField("jsonKey").apply {
    }

    private var jsonKeyOption by VaadinProp("", jsonKeyOptionField)


    private val root = ui {
        pageLayout {
            flexLayout {
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                flexWrap = FlexLayout.FlexWrap.WRAP
                add(inJsonTextArea)
                add(outJsonTextArea)
            }
            horizontalLayout {
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                formLayout {
                    add(jsonKeyOptionField)
                    button("格式化") {
                        pointerStyle()
                        onLeftClick {
                            formatJson()
                        }
                    }
                    button("转换") {
                        pointerStyle()
                        onLeftClick {
                            transform()
                        }
                    }
                }
            }

        }
    }

    fun formatJson() {
        try {
            var json = mapper.readTree(inJson)

            if (!jsonKeyOption.isNullOrBlank()) {
                if (json.isArray) {
                    json = mapper.convertValue(
                        mapper.convertValue<Array<Map<String, Any?>>>(json)
                            .asSequence()
                            .associateBy { it[jsonKeyOption] }
                    )
                }
            }

            outJson = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(json)
            logger.debug { outJson }
        } catch (e: Exception) {
            if (logger.isDebugEnabled) {
                logger.debug(e.message, e)
            }
            outJson = "ErrorJson"
            outJsonTextArea.isInvalid = true
        }
    }

    fun transform() {
        if (outJsonTextArea.isInvalid) {
            return
        }
        inJson = outJson
    }

}