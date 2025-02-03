package org.mj.passiveincome.app.api.config.security

import org.mj.passiveincome.domain.common.email.Email
import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserRepository
import org.mj.passiveincome.type.common.OAuth2ProviderType
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SecurityConfig(
  private val userRepository: UserRepository,
) {

  @Bean
  @ConditionalOnProperty(
    prefix = "app.security",
    name = ["create-test-user"],
    havingValue = "true",
  )
  fun runner(): CommandLineRunner {
    return CommandLineRunner {
      userRepository.save(
        User(
          email = Email.of("doif.dobby@gmail.com"),
          name = "Dobby",
          oauth2Subject = "115619350290774763145",
          oauth2ProviderType = OAuth2ProviderType.GOOGLE,
        )
      )
    }
  }
}
