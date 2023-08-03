package universe.sparkle.moduleservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ModuleServiceApplication

fun main(args: Array<String>) {
    runApplication<ModuleServiceApplication>(*args)
}
