package io.heartpattern.springfox.paper.brigadier

import io.heartpattern.springfox.paper.core.PaperAutoConfiguration
import io.heartpattern.springfox.paper.core.SpringFoxPlugin
import io.heartpattern.springfox.paper.core.command.CommandRegistrationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(PaperAutoConfiguration::class)
class BrigadierAutoConfiguration(
    private val plugin: SpringFoxPlugin,
    private val commandRegistrationService: CommandRegistrationService,
) {
    @get: Bean
    val brigadierCommandRegistrationService
        get() = BrigadierCommandRegistrationService(commandRegistrationService)

    @get: Bean
    val brigadierCommandRegistrar
        get() = BrigadierCommandRegistrar(plugin, brigadierCommandRegistrationService)
}