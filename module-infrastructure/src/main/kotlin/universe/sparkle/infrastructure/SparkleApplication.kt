package universe.sparkle.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["universe.sparkle.infrastructure", "universe.sparkle.usecase"])
class SparkleApplication

fun main(args: Array<String>) {
    runApplication<SparkleApplication>(*args)
}
