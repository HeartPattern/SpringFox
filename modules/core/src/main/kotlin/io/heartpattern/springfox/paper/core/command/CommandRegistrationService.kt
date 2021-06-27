package io.heartpattern.springfox.paper.core.command

import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.plugin.Plugin
import org.springframework.context.annotation.Bean
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

class CommandRegistrationService {
    @get:Bean
    val commandMap: CommandMap = (Bukkit.getServer()::class
        .declaredMemberProperties.find { it.name == "commandMap" } as KProperty1<Server, CommandMap>).run {
        isAccessible = true
        get(Bukkit.getServer())
    }

    fun syncCommand() {
        Bukkit.getServer()::class.declaredMemberFunctions.find { it.name == "syncCommands" }!!.call(Bukkit.getServer())
    }

    fun getOrRegister(name: String, plugin: Plugin, supplier: () -> Command): Command {
        return commandMap.getCommand(name) ?: run {
            val command = supplier()
            commandMap.register(name, plugin.name, command)
            syncCommand()
            command
        }
    }

    fun get(name: String): Command?{
        return commandMap.getCommand(name)
    }
}