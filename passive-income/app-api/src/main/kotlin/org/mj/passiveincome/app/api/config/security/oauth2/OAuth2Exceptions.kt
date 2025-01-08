package org.mj.passiveincome.app.api.config.security.oauth2

import org.mj.passiveincome.system.core.base.BaseException

class OAuth2AuthenticationException : BaseException(messageProperty = "error.authentication.oauth2") {
}
