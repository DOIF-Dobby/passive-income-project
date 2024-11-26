package org.mj.passiveincome.core.base

enum class BaseStatus(
  val code: String,
  val message: String,
) {
  SUCCESS("00", "Success"),
  FAIL("99", "Fail"),
  INVALID_PARAMETER("100", "Invalid parameter"),
  NOT_FOUND("200", "Not found"),
  INTERNAL_SERVER_ERROR("500", "Internal server error"),
  UNAUTHORIZED("900", "Unauthorized"),
  FORBIDDEN("901", "Forbidden"),
  EXPIRED_TOKEN("902", "Expired token"),
  INVALID_TOKEN("903", "Invalid token"),
  INVALID_REFRESH_TOKEN("904", "Invalid refresh token"),
  INVALID_ACCESS_TOKEN("905", "Invalid access token"),
  INVALID_USER("906", "Invalid user"),
  INVALID_PASSWORD("907", "Invalid password"),
  INVALID_EMAIL("908", "Invalid email"),
  INVALID_PHONE("909", "Invalid phone"),
  INVALID_NAME("910", "Invalid name"),
  INVALID_NICKNAME("9ß11", "Invalid nickname"),
}
