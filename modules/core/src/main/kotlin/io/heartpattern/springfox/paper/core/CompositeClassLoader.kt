package io.heartpattern.springfox.paper.core

import java.io.InputStream
import java.net.URL
import java.util.*

/**
 * ClassLoader composite several other class loaders
 */
class CompositeClassLoader(
    private val classLoaders: List<ClassLoader>
) : ClassLoader() {
    private fun <T> findAny(finder: ClassLoader.() -> T?): T? {
        for (classLoader in classLoaders) {
            val value = classLoader.finder()
            if (value != null)
                return value
        }

        return null
    }

    override fun getResourceAsStream(name: String?): InputStream? {
        return findAny { getResourceAsStream(name) }
    }

    override fun findClass(name: String?): Class<*> {
        for (classLoader in classLoaders) {
            try {
                return classLoader.loadClass(name)
            } catch (e: ClassNotFoundException) {
                // Nothing
            }
        }

        throw ClassNotFoundException(name)
    }

    override fun findResource(name: String?): URL? {
        return findAny { findResource(name) }
    }

    override fun findResources(name: String?): Enumeration<URL> {
        val result = classLoaders.flatMap {
            it.getResources(name).toList()
        }

        return Collections.enumeration(result)
    }
}