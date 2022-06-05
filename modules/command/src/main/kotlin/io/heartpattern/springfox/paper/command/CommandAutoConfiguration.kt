package io.heartpattern.springfox.paper.command

import io.heartpattern.springfox.paper.core.PaperAutoConfiguration
import io.heartpattern.springfox.paper.core.SpringFoxPlugin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(PaperAutoConfiguration::class)
class CommandAutoConfiguration(
    private val plugin: SpringFoxPlugin
){
    @get: Bean
    val commandRegistrationService
        get() = CommandRegistrationService()

    @get:Bean
    val commandHandlerRegistrar
        get() = CommandHandlerRegistrar(plugin, commandRegistrationService)

    @get: Bean
    val tabCompleterRegistrar
        get() = TabCompleterRegistrar(commandRegistrationService)
}