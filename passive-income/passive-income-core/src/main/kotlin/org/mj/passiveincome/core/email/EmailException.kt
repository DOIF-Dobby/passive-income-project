package org.mj.passiveincome.core.email

open class EmailException(message: String) : RuntimeException(message) {
}

class InvalidEmailException : EmailException("Invalid email")
