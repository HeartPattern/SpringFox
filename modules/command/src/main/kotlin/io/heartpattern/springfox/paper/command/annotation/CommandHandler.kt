package io.heartpattern.springfox.paper.command.annotation

/**
 * Annotate command handler
 * @see io.heartpattern.springfox.paper.command.CommandHandlerRegistrar
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CommandHandler(
    vararg val name: String,
    val description: String = "",
    val permission: String = "",
    val permissionMessage: String = "",
    val usage: String = ""
)