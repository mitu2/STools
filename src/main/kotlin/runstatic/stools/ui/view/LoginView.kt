package runstatic.stools.ui.view

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.flexLayout
import com.vaadin.flow.component.login.LoginForm
import com.vaadin.flow.component.login.LoginI18n
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import runstatic.stools.ui.component.PageFooter


/**
 *
 * @author chenmoand
 */
@Route("login")
@PageTitle("登录页 - static.run")
@SpringComponent
@UIScope
class LoginView @Autowired constructor(

) : KComposite(){

    private val loginForm = LoginForm().apply {
    }

    private val root = ui {
        flexLayout {
            justifyContentMode = FlexComponent.JustifyContentMode.CENTER
            add(loginForm)
            add(PageFooter())
        }
    }

    init {
        useZhCnLoginI18n()
        loginForm.setI18n(i18n)
    }


    companion object {

        private var i18n = LoginI18n.createDefault()

        fun useZhCnLoginI18n() {
            val i18nForm: LoginI18n.Form = i18n.form
            i18nForm.title = "登陆页面"
            i18nForm.username = "用户名"
            i18nForm.password = "密码"
            i18nForm.submit = "提交"
            i18nForm.forgotPassword = "忘记密码?"

            i18n.form = i18nForm

            val i18nErrorMessage = i18n.errorMessage
            i18nErrorMessage.title = "错误的用户名或密码"
            i18nErrorMessage.message = "请检查您的用户名和密码是否正确，然后重试。"
            i18n.errorMessage = i18nErrorMessage

        }

    }

}