package io.heartpattern.springfox.paper.core.event

import io.heartpattern.springfox.common.AnnotatedMethodScanner
import mu.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import java.lang.reflect.Method

class EventHandlerRegistrar : AnnotatedMethodScanner<EventHandler>(
    EventHandler::class
) {
    @Autowired
    private lateinit var plugin: Plugin
    private val logger = KotlinLogging.logger {}

    override fun postProcessAfterMethodInitialize(
        bean: Any,
        beanName: String,
        method: Method,
        annotation: EventHandler
    ) {
        method.isAccessible = true

        if (method.parameterCount != 1) {
            logger.error("Cannot register EventHandler for $method since it does not have exactly one parameter")
            return
        }

        val eventType = MethodParameter(method, 0).withContainingClass(bean::class.java).parameterType

        if (!Event::class.java.isAssignableFrom(eventType)) {
            logger.error("Cannot register EventHandler for $method since it has parameter that does not extends Event")
            return
        }

        @Suppress("UNCHECKED_CAST")
        Bukkit.getPluginManager().registerEvent(
            eventType as Class<out Event>,
            MethodListener(beanName, method),
            annotation.priority,
            { _, event ->
                if (eventType.isInstance(event))
                    method.invoke(bean, event)
            },
            plugin,
            annotation.ignoreCancelled
        )

        logger.trace { "Register EventHandler for $method in $beanName" }
    }

    override fun postProcessBeforeMethodDestruction(
        bean: Any,
        beanName: String,
        method: Method,
        annotation: EventHandler
    ) {
        HandlerList.unregisterAll(MethodListener(beanName, method))
        logger.trace { "Unregister EventHandler for $method in $beanName" }
    }

    private data class MethodListener(
        private val beanName: String,
        private val method: Method
    ) : Listener
}