package org.mj.passiveincome.domain.common.email

import jakarta.persistence.Embeddable
import java.util.regex.Pattern

@Embeddable
class Email(
  val address: String
) {

  init {
    require(isValid(address)) { throw InvalidEmailException() }
  }

  companion object {
    const val EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    val PATTERN = Pattern.compile(EMAIL_REGEX)

    fun isValid(rawEmail: String): Boolean {
      return PATTERN.matcher(rawEmail).matches()
    }

    fun of(rawEmail: String): Email {
      return Email(rawEmail)
    }
  }
}
