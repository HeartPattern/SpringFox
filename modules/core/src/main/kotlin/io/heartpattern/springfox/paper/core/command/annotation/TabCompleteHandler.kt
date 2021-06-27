package io.heartpattern.springfox.paper.core.command.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TabCompleteHandler(
    val name: String
)
