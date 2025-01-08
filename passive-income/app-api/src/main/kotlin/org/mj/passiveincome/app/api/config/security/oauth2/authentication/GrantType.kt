package org.mj.passiveincome.app.api.config.security.oauth2.authentication

enum class GrantType(
  val value: String
) {
  AUTHORIZATION_CODE("authorization_code"),
  CLIENT_CREDENTIALS("client_credentials"),
  PASSWORD("password"),
  REFRESH_TOKEN("refresh_token"),
  DEVICE_CODE("urn:ietf:params:oauth:grant-type:device_code"),
  TOKEN_EXCHANGE("urn:ietf:params:oauth:grant-type:token-exchange");

}
