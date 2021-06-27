package io.heartpattern.springfox.paper.core.command

import io.heartpattern.springfox.common.AnnotatedMethodScanner
import io.heartpattern.springfox.paper.core.command.annotation.TabCompleteHandler
import io.heartpattern.springfox.paper.core.command.model.SpringFoxPluginCommand
import org.springframework.core.Ordered
import java.lang.reflect.Method

class TabCompleterRegistrar(
    private val commandRegistrationService: CommandRegistrationService
) : AnnotatedMethodScanner<TabCompleteHandler>(
    TabCompleteHandler::class
) {
    override fun postProcessAfterMethodInitialize(
        bean: Any,
        beanName: String,
        method: Method,
        annotation: TabCompleteHandler
    ) {
        method.isAccessible = true

        (commandRegistrationService.get(annotation.name) as SpringFoxPluginCommand).completer = { invocation ->
            method.invoke(bean, invocation) as List<String>
        }
    }

    override fun postProcessBeforeMethodDestruction(
        bean: Any,
        beanName: String,
        method: Method,
        annotation: TabCompleteHandler
    ) {
        (commandRegistrationService.get(annotation.name) as SpringFoxPluginCommand?)?.completer = null
    }

    override fun getOrder() = Ordered.LOWEST_PRECEDENCE
}