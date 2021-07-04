package io.heartpattern.springfox.paper.core.command

import io.heartpattern.springfox.common.AnnotatedMethodScanner
import io.heartpattern.springfox.paper.core.command.annotation.CommandHandler
import io.heartpattern.springfox.paper.core.command.model.CommandInvocation
import io.heartpattern.springfox.paper.core.command.model.SpringFoxPluginCommand
import org.bukkit.plugin.Plugin
import org.springframework.core.Ordered
import java.lang.reflect.Method

/**
 * Registering method annotated with [CommandHandler] as command handler.
 * Method should take [CommandInvocation] as parameter
 */
open class CommandHandlerRegistrar(
    private val plugin: Plugin,
    private val commandRegistrationService: CommandRegistrationService
) : AnnotatedMethodScanner<CommandHandler>(
    CommandHandler::class
) {
    override fun postProcessAfterMethodInitialize(
        bean: Any,
        beanName: String,
        method: Method,
        annotation: CommandHandler
    ) {
        method.isAccessible = true

        (commandRegistrationService.getOrRegister(
            annotation.name.first(),
            plugin
        ) {
            SpringFoxPluginCommand(annotation)
        } as SpringFoxPluginCommand).executor = { invocation ->
            method.invoke(bean, invocation)
        }
    }

    override fun postProcessBeforeMethodDestruction(
        bean: Any,
        beanName: String,
        method: Method,
        annotation: CommandHandler
    ) {
        (commandRegistrationService.get(annotation.name.first()) as SpringFoxPluginCommand?)?.executor = null
    }

    override fun getOrder() = Ordered.LOWEST_PRECEDENCE - 1
}