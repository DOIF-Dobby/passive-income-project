package org.mj.passiveincome.system.security.oauth2.redirect

import org.mj.passiveincome.type.common.OAuth2ProviderType
import java.net.URLEncoder

interface OAuth2RedirectUriService {

  fun resolveRedirectUri(): String

  fun supports(providerType: OAuth2ProviderType): Boolean

  fun encodeURI(uri: String): String {
    return URLEncoder.encode(uri, "UTF-8").replace("+", "%20")
  }
}
