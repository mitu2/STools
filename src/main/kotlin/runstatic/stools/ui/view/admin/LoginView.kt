package runstatic.stools.ui.view.admin

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.flexLayout
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.login.LoginI18n
import com.vaadin.flow.component.login.LoginOverlay
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import runstatic.stools.configuration.security.CustomRequestCache
import runstatic.stools.util.pageLayout


/**
 *
 * @author chenmoand
 */
@Route(LoginView.ROUTE)
@PageTitle("登录页 - static.run")
@SpringComponent
@UIScope
class LoginView @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val requestCache: CustomRequestCache,
) : KComposite() {

    private val loginForm = LoginOverlay().apply {
        isOpened = true
        setTitle("登陆页")
        description = "STools Login Page"
        addLoginListener {
            login(it.username, it.password)
        }
    }

    private val root = ui {
        pageLayout {
            flexLayout {
                justifyContentMode = FlexComponent.JustifyContentMode.CENTER
                add(loginForm)
            }
        }
    }

    init {
        useZhCnLoginI18n()
        loginForm.setI18n(i18n)
    }


    fun login(username: String, password: String) {
        try {
            val authentication: Authentication = authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken(username, password))
            SecurityContextHolder.getContext().authentication = authentication
            loginForm.close()
            UI.getCurrent().navigate(requestCache.resolveRedirectUrl())
        } catch (ex: AuthenticationException) {
            loginForm.isError = true
        }
    }


    companion object {

        const val ROUTE = "login"

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