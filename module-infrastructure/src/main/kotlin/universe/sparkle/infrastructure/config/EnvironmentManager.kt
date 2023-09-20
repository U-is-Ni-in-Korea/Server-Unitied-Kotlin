package universe.sparkle.infrastructure.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class EnvironmentManager @Autowired constructor(
    private val environment: Environment,
) {

    val isDevelopment = environment.activeProfiles.contains("dev")
}
