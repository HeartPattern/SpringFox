package io.heartpattern.springfox.paper.core.event

import io.heartpattern.springfox.common.AnnotatedMethodScanner
import io.heartpattern.springfox.paper.core.SpringFoxPlugin
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.springframework.core.MethodParameter
import java.lang.reflect.Method
import kotlin.reflect.KParameter
import kotlin.reflect.full.callSuspend
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.kotlinFunction

/**
 * Register all method annotated with [EventHandler] in bean
 */
open class EventHandlerRegistrar(
    private val plugin: SpringFoxPlugin
) : AnnotatedMethodScanner<EventHandler>(
    EventHandler::class
) {
    private val logger = KotlinLogging.logger {}

    override fun postProcessAfterMethodInitialize(
        bean: Any,
        beanName: String,
        method: Method,
        annotation: EventHandler
    ) {
        method.isAccessible = true
        val kotlinFunction = method.kotlinFunction
        kotlinFunction?.isAccessible = true

        if (method.parameterCount != 1 && kotlinFunction?.parameters?.filter { it.kind != KParameter.Kind.INSTANCE }?.size != 1) {
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
            if (kotlinFunction == null || !kotlinFunction.isSuspend) {
                { _, event ->
                    if (eventType.isInstance(event)) {
                        method.invoke(bean, event)
                    }
                }
            } else {
                { _, event ->
                    if (eventType.isInstance(event)) {
                        plugin.launch {
                            kotlinFunction.callSuspend(bean, event)
                        }
                    }
                }
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
