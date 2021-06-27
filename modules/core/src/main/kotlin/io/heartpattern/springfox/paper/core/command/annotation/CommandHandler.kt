package io.heartpattern.springfox.paper.core.command.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CommandHandler(
    vararg val name: String,
    val description: String = "",
    val permission: String = "",
    val permissionMessage: String = "",
    val usage: String = ""
)