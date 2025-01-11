package org.mj.passiveincome.type.common

enum class OAuth2ProviderType {
  GOOGLE,
  ;

  companion object {
    fun of(value: String, throwBlock: () -> Nothing): OAuth2ProviderType {
      return entries.firstOrNull { it.name == value.uppercase() }
        ?: throwBlock()
    }
  }
}
