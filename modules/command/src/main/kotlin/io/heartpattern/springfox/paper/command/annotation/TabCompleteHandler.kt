package io.heartpattern.springfox.paper.command.annotation

/**
 * Annotate command handler
 * @see io.heartpattern.springfox.paper.command.TabCompleterRegistrar
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TabCompleteHandler(
    val name: String
)
