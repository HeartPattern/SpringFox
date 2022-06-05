package io.heartpattern.springfox.paper.core

import io.heartpattern.springfox.paper.core.event.EventHandlerRegistrar
import io.heartpattern.springfox.paper.core.service.BukkitServiceBeanRegistrar
import org.bukkit.plugin.java.PluginClassLoader
import org.springframework.beans.factory.getBeanNamesForAnnotation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Autoconfiguration module for springfox
 */
@Configuration
class PaperAutoConfiguration : ApplicationContextAware {
    private lateinit var applicationContext: ApplicationContext

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    // Plugin
    @get: Bean
    val plugin: SpringFoxPlugin
        get() {
            val application = applicationContext.getBeanNamesForAnnotation<SpringBootApplication>().first()
            val bean = applicationContext.getBean(application)
            return (bean::class.java.classLoader as PluginClassLoader).plugin as SpringFoxPlugin
        }

    // Services

    @get: Bean
    val bukkitServiceBeanRegistrar
        get() = BukkitServiceBeanRegistrar()

    // Events

    @get: Bean
    val eventHandlerRegistrar
        get() = EventHandlerRegistrar(plugin)
}