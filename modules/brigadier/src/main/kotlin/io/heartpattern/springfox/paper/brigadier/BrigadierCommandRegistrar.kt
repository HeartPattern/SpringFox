package io.heartpattern.springfox.paper.brigadier

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.tree.LiteralCommandNode
import org.bukkit.plugin.Plugin
import org.springframework.beans.factory.config.BeanPostProcessor

class BrigadierCommandRegistrar(
    private val plugin: Plugin,
    private val brigadierCommandRegistrationService: BrigadierCommandRegistrationService
) : BeanPostProcessor {
    @Suppress("UNCHECKED_CAST")
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        if (bean is LiteralCommandNode<*>) {
            brigadierCommandRegistrationService.register(
                plugin,
                bean as LiteralCommandNode<BukkitBrigadierCommandSource>
            )
        }
        return bean
    }
}