package io.heartpattern.springfox.paper.brigadier

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.destroystokyo.paper.event.brigadier.AsyncPlayerSendCommandsEvent
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.tree.LiteralCommandNode
import io.heartpattern.springfox.paper.core.command.CommandRegistrationService
import io.heartpattern.springfox.paper.core.command.model.CommandInvocation
import io.heartpattern.springfox.paper.core.command.model.SpringFoxPluginCommand
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.BlockCommandSender
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.plugin.Plugin

/**
 * Service that inject brigadier command into bukkit system
 */
open class BrigadierCommandRegistrationService(
    private val commandRegistrationService: CommandRegistrationService
) {
    private val dispatcher = CommandDispatcher<BukkitBrigadierCommandSource>()

    fun register(plugin: Plugin, node: LiteralCommandNode<BukkitBrigadierCommandSource>) {
        dispatcher.root.addChild(node)
        (commandRegistrationService.getOrRegister(node.name, plugin) {
            SpringFoxPluginCommand(
                node.name,
                node.usageText,
                node.usageText,
                emptyList()
            )
        } as SpringFoxPluginCommand).executor = { dispatch(it) }
    }

    @Suppress("unused")
    @EventHandler
    fun AsyncPlayerSendCommandsEvent<BukkitBrigadierCommandSource>.onCommandSent() {
        if (isAsynchronous || !hasFiredAsync())
            dispatcher.root.children.forEach(commandNode::addChild)
    }

    private fun dispatch(invocation: CommandInvocation) {
        val originalCommand = invocation.command.name + " " + invocation.args.joinToString(" ")
        dispatcher.execute(originalCommand, BukkitBrigadierCommandSourceImpl(invocation.sender))
    }

    private class BukkitBrigadierCommandSourceImpl(
        private val commandSender: CommandSender
    ) : BukkitBrigadierCommandSource {
        override fun getBukkitEntity(): Entity? {
            return commandSender as? Entity
        }

        override fun getBukkitWorld(): World? {
            return bukkitEntity?.world ?: (commandSender as? BlockCommandSender)?.block?.world
        }

        override fun getBukkitLocation(): Location? {
            return bukkitEntity?.location ?: (commandSender as? BlockCommandSender)?.block?.location
        }

        override fun getBukkitSender(): CommandSender {
            return commandSender
        }
    }
}