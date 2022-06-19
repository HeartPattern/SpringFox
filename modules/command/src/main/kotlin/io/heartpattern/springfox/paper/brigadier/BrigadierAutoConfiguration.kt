package io.heartpattern.springfox.paper.brigadier

import io.heartpattern.springfox.paper.command.CommandAutoConfiguration
import io.heartpattern.springfox.paper.command.CommandRegistrationService
import io.heartpattern.springfox.paper.core.PaperAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * Autoconfiguration module for brigadier
 */
@Configuration
@Import(PaperAutoConfiguration::class, CommandAutoConfiguration::class)
class BrigadierAutoConfiguration(
    private val paperAutoConfiguration: PaperAutoConfiguration,
    private val commandRegistrationService: CommandRegistrationService,
) {
    @get: Bean
    val brigadierCommandRegistrationService
        get() = BrigadierCommandRegistrationService(commandRegistrationService)

    @get: Bean
    val brigadierCommandRegistrar
        get() = BrigadierCommandRegistrar(paperAutoConfiguration.plugin, brigadierCommandRegistrationService)
}