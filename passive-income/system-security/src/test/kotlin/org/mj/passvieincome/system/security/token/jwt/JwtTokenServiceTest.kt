package org.mj.passvieincome.system.security.token.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.longs.shouldBeLessThan
import io.kotest.matchers.shouldBe
import org.mj.passiveincome.system.security.token.Token
import org.mj.passiveincome.system.security.token.jwt.HmacKeyProvider
import org.mj.passiveincome.system.security.token.jwt.JwtTokenContext
import org.mj.passiveincome.system.security.token.jwt.JwtTokenService
import java.time.Duration

class JwtTokenServiceTest : DescribeSpec({
  describe("generateToken") {
    it("JWT 토큰 생성") {
      val context = JwtTokenContext(
        subject = "test 홍홍홍",
        expireDuration = Duration.ofHours(2L),
        payload = mapOf("email" to "ddong@gmail.com")
      )

      val token = tokenService.generateToken(context)

      val parsedToken = getParsedToken(token)
      val expectedExpiration = token.getExpiration().toInstant()
      val actualExpiration = parsedToken.payload.expiration.toInstant()

      parsedToken.payload.subject shouldBe "test 홍홍홍"
      parsedToken.payload["email"] shouldBe "ddong@gmail.com"
      Duration.between(expectedExpiration, actualExpiration).abs().seconds shouldBeLessThan 1

    }
  }

  describe("validateToken") {
    context("유효한 토큰 경우") {
      val context = JwtTokenContext(
        subject = "test 홍홍홍",
        expireDuration = Duration.ofHours(2L)
      )

      val token = tokenService.generateToken(context)

      it("true를 반환한다.") {
        tokenService.validateToken(token.value()) shouldBe true
      }
    }

    context("만료된 토큰인 경우") {
      it("false를 반환한다.") {
        val context = JwtTokenContext(
          subject = "test 홍홍홍",
          expireDuration = Duration.ofSeconds(-1L)
        )

        val token = tokenService.generateToken(context)

        tokenService.validateToken(token.value()) shouldBe false
      }
    }

    context("토큰 값이 변경된 경우") {
      it("false를 반환한다.") {
        val context = JwtTokenContext(
          subject = "test 홍홍홍",
          expireDuration = Duration.ofHours(2L)
        )

        val token = tokenService.generateToken(context)

        tokenService.validateToken("${token.value()}invalid") shouldBe false
      }
    }
  }

  describe("getPayloadValue") {
    context("payloadKey 해당하는 값이 있는 경우") {
      val context = JwtTokenContext(
        subject = "test 홍홍홍",
        expireDuration = Duration.ofHours(2L),
        payload = mapOf("email" to "ddong@gmail.com")
      )

      val token = tokenService.generateToken(context)

      it("해당 값을 반환한다.") {
        tokenService.getPayloadValue(token.value(), "email") shouldBe "ddong@gmail.com"
      }
    }

    context("payloadKey 해당하는 값이 없는 경우") {
      val context = JwtTokenContext(
        subject = "test 홍홍홍",
        expireDuration = Duration.ofHours(2L)
      )

      val token = tokenService.generateToken(context)

      it("null을 반환한다.") {
        tokenService.getPayloadValue(token.value(), "email") shouldBe null
      }
    }
  }

}) {
  companion object {
    private const val PLAIN_TEXT = "123456789012345678901234567890123456789012345678901234567890"
    private val tokenKeyProvider = HmacKeyProvider(PLAIN_TEXT)
    val tokenService = JwtTokenService(tokenKeyProvider)

    fun getParsedToken(token: Token): Jws<Claims> {
      val secretKey = tokenKeyProvider.getKey()

      return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token.value())
    }
  }
}
