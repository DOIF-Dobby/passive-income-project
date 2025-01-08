package org.mj.passiveincome.app.api.config.security.oauth2.authentication.google

import com.fasterxml.jackson.core.type.TypeReference
import org.mj.passiveincome.app.api.config.security.GoogleOAuth2Properties
import org.mj.passiveincome.app.api.config.security.oauth2.OAuth2AuthenticationException
import org.mj.passiveincome.app.api.config.security.oauth2.OAuth2ParameterNames
import org.mj.passiveincome.app.api.config.security.oauth2.authentication.BasicOAuth2Authentication
import org.mj.passiveincome.app.api.config.security.oauth2.authentication.GrantType
import org.mj.passiveincome.app.api.config.security.oauth2.authentication.OAuth2Authentication
import org.mj.passiveincome.app.api.config.security.oauth2.authentication.OAuth2AuthenticationService
import org.mj.passiveincome.domain.user.OAuth2ProviderType
import org.mj.passiveincome.system.core.extensions.toQueryParams
import org.mj.passiveincome.system.security.token.jwt.JwtUtils
import org.mj.passiveincome.system.web.databind.ApiObjectMapper
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class GoogleOAuth2AuthenticationService(
  private val restClient: RestClient,
  private val properties: GoogleOAuth2Properties,
  private val objectMapper: ApiObjectMapper,
) : OAuth2AuthenticationService {


  override fun authenticate(authorizationCode: String, redirectUri: String): OAuth2Authentication {

    // grant_type = authorization_code 일 때 access token 요청을 위한 파라미터
    val tokenRequest = mapOf(
      OAuth2ParameterNames.GRANT_TYPE to GrantType.AUTHORIZATION_CODE.value,
      OAuth2ParameterNames.CODE to authorizationCode,
      OAuth2ParameterNames.REDIRECT_URI to redirectUri,
      OAuth2ParameterNames.CLIENT_ID to properties.clientId,
      OAuth2ParameterNames.CLIENT_SECRET to properties.clientSecret
    )

    // 요청 보내기
    val response = restClient.post()
      .uri(TOKEN_ENDPOINT)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(tokenRequest.toQueryParams()) // 쿼리 파라미터로 변환
      .retrieve()
      .toEntity(GoogleOAuth2AccessTokenResponse::class.java)

    val tokenResponse = response.body ?: throw OAuth2AuthenticationException()
    println("tokenResponse = ${tokenResponse}")
    val payload = JwtUtils.decodePayload(tokenResponse.idToken)

    val payloadMap = objectMapper.readValue(payload, object : TypeReference<Map<String, Any>>() {})

    return BasicOAuth2Authentication(
      subject = payloadMap["sub"] as String,
      email = payloadMap["email"] as String,
      providerType = OAuth2ProviderType.GOOGLE
    )
  }

  override fun supports(oauth2ProviderType: OAuth2ProviderType): Boolean {
    return oauth2ProviderType == OAuth2ProviderType.GOOGLE
  }

  companion object {
    private const val TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token"
  }
}
