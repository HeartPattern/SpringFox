package io.heartpattern.springfox.paper.core

import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

abstract class SpringFoxPlugin : JavaPlugin() {
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
}