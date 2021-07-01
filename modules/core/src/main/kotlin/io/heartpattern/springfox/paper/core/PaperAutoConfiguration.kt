package io.heartpattern.springfox.paper.core

import io.heartpattern.springfox.paper.core.command.CommandHandlerRegistrar
import io.heartpattern.springfox.paper.core.command.CommandRegistrationService
import io.heartpattern.springfox.paper.core.command.TabCompleterRegistrar
import io.heartpattern.springfox.paper.core.event.EventHandlerRegistrar
import io.heartpattern.springfox.paper.core.service.BukkitServiceBeanRegistrar
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.PluginClassLoader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Autoconfiguration module for springfox
 */
@Configuration
class PaperAutoConfiguration(
    private val plugin: JavaPlugin
) {
    // Commands

    @get: Bean
    val commandRegistrationService
        get() = CommandRegistrationService()

    @get:Bean
    val commandHandlerRegistrar
        get() = CommandHandlerRegistrar(plugin, commandRegistrationService)

    @get: Bean
    val tabCompleterRegistrar
        get() = TabCompleterRegistrar(commandRegistrationService)

    // Services

    @get: Bean
    val bukkitServiceBeanRegistrar
        get() = BukkitServiceBeanRegistrar()

    // Events

    @get: Bean
    val eventHandlerRegistrar
        get() = EventHandlerRegistrar()
}