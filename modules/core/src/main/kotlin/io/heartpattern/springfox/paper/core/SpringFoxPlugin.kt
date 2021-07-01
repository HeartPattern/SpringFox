package io.heartpattern.springfox.paper.core

import io.heartpattern.springfox.paper.core.coroutine.CoroutinePlugin
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import java.util.logging.Level
import kotlin.coroutines.CoroutineContext

/**
 * SpringFox plugin entrypoint.
 */
abstract class SpringFoxPlugin : JavaPlugin(), CoroutineScope {
    private lateinit var applicationContext: ConfigurableApplicationContext

    final override fun onEnable() {
        val currentClassLoader = Thread.currentThread().contextClassLoader
        Thread.currentThread().contextClassLoader = classLoader
        applicationContext = SpringApplication(this::class.java).run()
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