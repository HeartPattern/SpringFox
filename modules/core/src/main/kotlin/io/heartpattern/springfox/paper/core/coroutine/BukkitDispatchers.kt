package io.heartpattern.springfox.paper.core.coroutine

import com.google.auto.service.AutoService
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.MainDispatcherFactory
import org.bukkit.plugin.Plugin
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext


internal data class CoroutinePlugin(val plugin: Plugin) : AbstractCoroutineContextElement(CoroutinePlugin) {
    companion object Key : CoroutineContext.Key<CoroutinePlugin>

    override fun toString(): String {
        return "CoroutinePlugin(${plugin.name})"
    }
}

@Suppress("unused")
@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
val Dispatchers.Bukkit: MainCoroutineDispatcher
    get() = BukkitDispatcher

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
internal sealed class DispatcherBukkit : MainCoroutineDispatcher(), Delay {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        org.bukkit.Bukkit.getScheduler().runTask(
            context[CoroutinePlugin]?.plugin
                ?: throw IllegalStateException("CoroutineContext have no CoroutinePlugin"),
            block
        )
    }

    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        val task = org.bukkit.Bukkit.getScheduler().runTaskLater(
            continuation.context[CoroutinePlugin]?.plugin
                ?: throw IllegalStateException("CoroutineContext have no CoroutinePlugin"), java.lang.Runnable {
                with(continuation) {
                    resumeUndispatched(Unit)
                }
            }, timeMillis / 50)
        continuation.invokeOnCancellation { task.cancel() }
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AutoService(MainDispatcherFactory::class)
internal class BukkitDispatcherFactory : MainDispatcherFactory {
    override val loadPriority: Int
        get() = 0

    override fun createDispatcher(allFactories: List<MainDispatcherFactory>): MainCoroutineDispatcher = BukkitDispatcher
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
private object ImmediateBukkitDispatcher : DispatcherBukkit() {
    override val immediate: MainCoroutineDispatcher
        get() = this

    override fun isDispatchNeeded(context: CoroutineContext): Boolean = !org.bukkit.Bukkit.isPrimaryThread()

    override fun toString() = "Bukkit [immediate]"
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
internal object BukkitDispatcher : DispatcherBukkit() {
    override val immediate: MainCoroutineDispatcher
        get() = ImmediateBukkitDispatcher

    override fun toString() = "Bukkit"
}

suspend fun delayTick(tick: Long) {
    delay(tick * 50)
}