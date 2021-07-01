package io.heartpattern.springfox.test

import io.heartpattern.springfox.paper.core.SpringFoxPlugin
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(
    scanBasePackages = ["io.heartpattern.springfox.test"]
)
class TestPlugin: SpringFoxPlugin()