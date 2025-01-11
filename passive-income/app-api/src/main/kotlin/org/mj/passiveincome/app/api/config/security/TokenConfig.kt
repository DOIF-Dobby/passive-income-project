package org.mj.passiveincome.app.api.config.security

import org.mj.passiveincome.system.security.token.TokenKeyProvider
import org.mj.passiveincome.system.security.token.TokenService
import org.mj.passiveincome.system.security.token.jwt.HmacKeyProvider
import org.mj.passiveincome.system.security.token.jwt.JwtTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenConfig(
  private val jwtAccessTokenProperties: JwtAccessTokenProperties
) {

  @Bean
  fun tokenKeyProvider(): TokenKeyProvider {
    return HmacKeyProvider(jwtAccessTokenProperties.secret)
  }

  @Bean
  fun tokenService(): TokenService {
    return JwtTokenService(tokenKeyProvider())
  }
}
