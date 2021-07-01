package io.heartpattern.springfox.common

import mu.KotlinLogging
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor
import org.springframework.core.MethodIntrospector
import org.springframework.core.Ordered
import org.springframework.core.annotation.AnnotatedElementUtils
import java.lang.reflect.Method
import kotlin.reflect.KClass

/**
 * Helper class that scan every method in every registering bean which is annotated with [A]
 */
abstract class AnnotatedMethodScanner<A : Annotation>(
    private val annotationType: KClass<A>
) : DestructionAwareBeanPostProcessor, Ordered {
    private val logger = KotlinLogging.logger {}

    /**
     * Process method annotated with [A] after bean initialize
     */
    abstract fun postProcessAfterMethodInitialize(bean: Any, beanName: String, method: Method, annotation: A)

    /**
     * Process method annotated with [A] before bean destroy
     */
    abstract fun postProcessBeforeMethodDestruction(bean: Any, beanName: String, method: Method, annotation: A)

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val methods = findAnnotatedMethods(bean)

        methods.forEach { (method, annotation) ->
            try {
                postProcessAfterMethodInitialize(bean, beanName, method, annotation)
            } catch (e: Exception) {
                logger.error(e) { "Exception thrown while post processing method after initialization: $beanName $method" }
            }
        }

        return bean
    }

    override fun postProcessBeforeDestruction(bean: Any, beanName: String) {
        val methods = findAnnotatedMethods(bean)

        methods.forEach { (method, annotation) ->
            try {
                postProcessBeforeMethodDestruction(bean, beanName, method, annotation)
            } catch (e: Exception) {
                logger.error(e) { "Exception thrown while post processing method before destruction: $beanName $method" }
            }
        }
    }

    override fun getOrder(): Int = Ordered.LOWEST_PRECEDENCE

    private fun findAnnotatedMethods(bean: Any): Map<Method, A> {
        return MethodIntrospector.selectMethods(
            bean::class.java,
            MethodIntrospector.MetadataLookup { method ->
                AnnotatedElementUtils.findMergedAnnotation(method, annotationType.java)
            }
        )
    }
}