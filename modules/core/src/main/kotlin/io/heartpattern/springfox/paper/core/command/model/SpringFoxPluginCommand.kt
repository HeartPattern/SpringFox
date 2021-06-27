package io.heartpattern.springfox.paper.core.command.model

import io.heartpattern.springfox.paper.core.command.annotation.CommandHandler
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

@OptIn(ExperimentalStdlibApi::class)
class SpringFoxPluginCommand(
    name: String,
    description: String,
    usage: String,
    alias: List<String>,
    var executor: ((CommandInvocation) -> Unit)? = null,
    var completer: ((TabCompletionInvocation) -> List<String>)? = null
) : Command(
    name,
    description,
    usage,
    alias
) {
    constructor(
        annotation: CommandHandler,
        executor: ((CommandInvocation) -> Unit)? = null,
        completer: ((TabCompletionInvocation) -> List<String>)? = null
    ) : this(
        annotation.name.first(),
        annotation.description,
        annotation.usage,
        annotation.name.drop(1),
        executor,
        completer
    )

    override fun execute(
        sender: CommandSender,
        commandLabel: String,
        args: Array<out String>
    ): Boolean {
        executor?.invoke(CommandInvocation(sender, this, commandLabel, args.toList()))
        return true
    }

    override fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        return completer?.invoke(TabCompletionInvocation(sender, this, alias, args.toList()))?.toMutableList()
            ?: mutableListOf()
    }
}