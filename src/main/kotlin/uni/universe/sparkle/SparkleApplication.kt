package uni.universe.sparkle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SparkleApplication

fun main(args: Array<String>) {
    runApplication<SparkleApplication>(*args)
}
