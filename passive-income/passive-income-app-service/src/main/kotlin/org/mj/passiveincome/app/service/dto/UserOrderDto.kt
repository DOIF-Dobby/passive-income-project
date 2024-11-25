package org.mj.passiveincome.app.service.dto

import org.mj.passiveincome.app.service.domain.UserOrder
import org.mj.passiveincome.core.money.Currency
import java.math.BigDecimal
import java.time.LocalDateTime

data class UserOrderDto(
  val id: Long,
  val orderDateTime: LocalDateTime,
  val fromAmount: BigDecimal,
  val fromCurrency: Currency,
  val toAmount: BigDecimal,
  val toCurrency: Currency,
) {

  companion object {
    fun from(entity: UserOrder): UserOrderDto {
      return UserOrderDto(
        id = entity.id!!,
        orderDateTime = entity.orderDate,
        fromAmount = entity.fromMoney.amount,
        fromCurrency = entity.fromMoney.currency,
        toAmount = entity.toMoney.amount,
        toCurrency = entity.toMoney.currency,
      )
    }
  }
}