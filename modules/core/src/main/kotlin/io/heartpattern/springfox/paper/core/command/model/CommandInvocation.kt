package io.heartpattern.springfox.paper.core.command.model

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

/**
 * Represent data provided on command invocation
 */
data class CommandInvocation(
    val sender: CommandSender,
    val command: Command,
    val label: String,
    val args: List<String>
)