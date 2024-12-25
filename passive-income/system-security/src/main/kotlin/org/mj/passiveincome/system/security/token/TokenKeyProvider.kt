package org.mj.passiveincome.system.security.token

import javax.crypto.SecretKey

interface TokenKeyProvider {

  fun getKey(): SecretKey
}
