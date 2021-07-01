package io.heartpattern.springfox.test

import io.heartpattern.springfox.paper.core.coroutine.delayTick
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component.*
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerJoinEvent
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class TestComponent(
    private val plugin: TestPlugin
) {
    @PostConstruct
    fun construct(){
        println("Constructed")
    }

    @EventHandler
    fun PlayerJoinEvent.onJoin(){
        plugin.launch{
            repeat(10){
                delayTick(20)
                player.sendMessage(text(it))
            }
        }
    }

    @PreDestroy
    fun destroy(){
        println("Destroyed")
    }
}