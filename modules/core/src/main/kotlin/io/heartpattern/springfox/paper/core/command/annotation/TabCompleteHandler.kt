package io.heartpattern.springfox.paper.core.command.annotation

/**
 * Annotate command handler
 * @see io.heartpattern.springfox.paper.core.command.TabCompleterRegistrar
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TabCompleteHandler(
    val name: String
)
