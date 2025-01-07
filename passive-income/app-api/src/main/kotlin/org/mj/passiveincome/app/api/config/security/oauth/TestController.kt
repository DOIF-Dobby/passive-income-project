package org.mj.passiveincome.app.api.config.security.oauth

import org.mj.passiveincome.system.core.base.BaseResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient

@RestController
class TestController(
  private val authorizedClientManager: OAuth2AuthorizedClientManager
) {

  @Value("\${spring.security.oauth2.client.registration.google.client-id}")
  private lateinit var clientId: String

  @Value("\${spring.security.oauth2.client.registration.google.client-secret}")
  private lateinit var clientSecret: String

  //  @PostMapping("/oauth2/test")
  fun test(@RequestBody request: AuthCodeRequest): BaseResponse {

    val clientRegistration = ClientRegistration
      .withRegistrationId("google")
      .clientId(clientId)
      .clientSecret(clientSecret)
      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
      .redirectUri(request.redirectUri)
      .tokenUri("https://oauth2.googleapis.com/token")
      .authorizationUri("https://accounts.google.com/o/oauth2/auth")
      .build()

    val clientRequest = OAuth2AuthorizationCodeGrantRequest(
      clientRegistration,
      OAuth2AuthorizationExchange(
        OAuth2AuthorizationRequest.authorizationCode()
          .redirectUri(request.redirectUri)
          .authorizationUri("https://accounts.google.com/o/oauth2/auth")
          .clientId(clientId)
          .build(),
        OAuth2AuthorizationResponse.success(request.code).redirectUri(request.redirectUri).build()
      )
    )

    val tokenResponseClient = RestClientAuthorizationCodeTokenResponseClient()
    val tokenResponse = tokenResponseClient.getTokenResponse(clientRequest)

    tokenResponse.additionalParameters.forEach { (key, value) ->
      println("$key = $value")
    }

    println("tokenResponse.accessToken = ${tokenResponse.accessToken}")
    println("tokenResponse.refreshToken = ${tokenResponse.refreshToken}")

    return BaseResponse.ok()
  }

  @PostMapping("/oauth2/test")
  fun test2(@RequestBody request: AuthCodeRequest): BaseResponse {

    val restClient = RestClient.create()
    val tokenEndpoint = "https://oauth2.googleapis.com/token"

    // Google로 Access Token 요청
    val tokenRequest = mapOf(
      OAuth2ParameterNames.GRANT_TYPE to "authorization_code",
      OAuth2ParameterNames.CODE to request.code,
      OAuth2ParameterNames.REDIRECT_URI to request.redirectUri,
      OAuth2ParameterNames.CLIENT_ID to clientId,
      OAuth2ParameterNames.CLIENT_SECRET to clientSecret
    )

    // 요청 보내기
    val response = restClient.post()
      .uri(tokenEndpoint)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(tokenRequest.toQueryParams()) // 쿼리 파라미터로 변환
      .retrieve()
      .toEntity(object : ParameterizedTypeReference<Map<String, Any>>() {})

    // Access Token 응답 반환
    val body = response.body ?: throw IllegalStateException("No response body")
    body.forEach {
      println("${it.key} = ${it.value}")
    }

    return BaseResponse.ok()
  }
}

data class AuthCodeRequest(
  val code: String,
  val redirectUri: String
)

fun Map<String, String>.toQueryParams(): String {
  return this.entries.joinToString("&") { (key, value) ->
    "${key}=${value}"
  }
}
