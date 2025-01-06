package org.mj.passiveincome.app.api.config.security

import org.mj.passiveincome.system.security.token.TokenKeyProvider
import org.mj.passiveincome.system.security.token.TokenService
import org.mj.passiveincome.system.security.token.jwt.HmacKeyProvider
import org.mj.passiveincome.system.security.token.jwt.JwtTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenConfig {

  @Bean
  fun tokenKeyProvider(): TokenKeyProvider {
    // TODO: 키를 어디에 저장해야할까요?
    return HmacKeyProvider("123456789012345678901234567890123456789012345678901234567890")
  }

  @Bean
  fun tokenService(): TokenService {
    return JwtTokenService(tokenKeyProvider())
  }
}
