package io.heartpattern.springfox.paper.core.service

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ConfigurableApplicationContext

/**
 * Register bukkit services as spring bean.
 */
open class BukkitServiceBeanRegistrar : ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        val beanFactory =
            ((applicationContext as ConfigurableApplicationContext).beanFactory as DefaultListableBeanFactory)

        Bukkit.getPluginManager().plugins.filter(Plugin::isEnabled).forEach { plugin ->
            beanFactory.registerSingleton(plugin.name, plugin)
        }

        Bukkit.getServicesManager().knownServices.forEach { services ->
            val impl = Bukkit.getServicesManager().getRegistration(services)
                ?: return@forEach

            beanFactory.registerSingleton(services.name, impl.provider)
        }
    }
}