package org.mj.passiveincome.system.security.token.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import org.mj.passiveincome.system.security.token.Token
import org.mj.passiveincome.system.security.token.TokenContext
import org.mj.passiveincome.system.security.token.TokenKeyProvider
import org.mj.passiveincome.system.security.token.TokenService
import java.util.Date

class JwtTokenService(
  private val tokenKeyProvider: TokenKeyProvider,
) : TokenService {

  /**
   * token을 생성한다.
   */
  override fun generateToken(context: TokenContext): Token {
    val secretKey = tokenKeyProvider.getKey()
    val issuedAt = Date()
    val expiredMills = issuedAt.time + context.getExpireDuration().toMillis()
    val expiration = Date(expiredMills)

    val jwt = Jwts.builder()
      .subject(context.getSubject())
      .claims(context.getPayload())
      .signWith(secretKey)
      .issuedAt(issuedAt)
      .expiration(expiration)
      .compact()

    return JwtToken(
      token = jwt,
    )
  }

  /**
   * token이 유효한지 검증한다.
   */
  override fun validateToken(token: String): Boolean {
    return try {
      val parsedToken = getParsedToken(token)

      val expiration = parsedToken.payload.expiration
      expiration.after(Date())
    } catch (e: Exception) {
      false
    }
  }

  /**
   * token의 모든 payload를 반환한다.
   */
  override fun getPayloadValues(token: String): Map<String, Any> {
    return try {
      val parsedToken = getParsedToken(token)
      parsedToken.payload
    } catch (e: Exception) {
      emptyMap()
    }
  }

  /**
   * token의 payload에서 payloadKey에 해당하는 값을 반환한다.
   */
  override fun getPayloadValue(token: String, payloadKey: String): Any? {
    return try {
      val parsedToken = getParsedToken(token)
      parsedToken.payload[payloadKey]
    } catch (e: Exception) {
      null
    }
  }

  /**
   * token을 파싱하여 Jws<Claims> 객체로 반환한다.
   */
  private fun getParsedToken(token: String): Jws<Claims> {
    return getJwtParser()
      .parseSignedClaims(token)
  }

  /**
   * JwtParser를 반환한다.
   */
  private fun getJwtParser(): JwtParser {
    val secretKey = tokenKeyProvider.getKey()

    return Jwts.parser()
      .verifyWith(secretKey)
      .build()
  }

}
