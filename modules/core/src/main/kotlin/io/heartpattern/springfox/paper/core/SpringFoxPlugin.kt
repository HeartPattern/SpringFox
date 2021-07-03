package io.heartpattern.springfox.paper.core

import io.heartpattern.springfox.paper.core.coroutine.CoroutinePlugin
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.SpringApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.io.DefaultResourceLoader
import java.util.*
import java.util.logging.Level
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

/**
 * SpringFox plugin entrypoint.
 */
abstract class SpringFoxPlugin(private val applicationClass: KClass<*>) : JavaPlugin(), CoroutineScope {
    private lateinit var applicationContext: ConfigurableApplicationContext

    final override fun onEnable() {
        val currentClassLoader = Thread.currentThread().contextClassLoader
        System.setProperty("org.springframework.boot.logging.LoggingSystem", "org.springframework.boot.logging.java.JavaLoggingSystem")
        Thread.currentThread().contextClassLoader = classLoader
        applicationContext = SpringApplication(applicationClass.java).apply{
            resourceLoader = DefaultResourceLoader(applicationClass.java.classLoader)
        }.run()
        Thread.currentThread().contextClassLoader = currentClassLoader
    }

    final override fun onDisable() {
        applicationContext.close()
    }

    override val coroutineContext: CoroutineContext = SupervisorJob() +
        Dispatchers.Main +
        CoroutinePlugin(this) +
        CoroutineExceptionHandler { ctx, throwable ->
            logger.log(Level.SEVERE, throwable) {
                "Uncatched exception from coroutine $ctx"
            }
        }
}