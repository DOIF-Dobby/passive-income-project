package org.mj.passiveincome.app.service.service

import org.mj.passiveincome.app.service.domain.UserOrder
import org.mj.passiveincome.app.service.domain.UserOrderRepository
import org.mj.passiveincome.core.money.Money
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserOrderService(
	private val userOrderRepository: UserOrderRepository
) {

	@Transactional
	fun createOrder() {
		val exchangeRateValue = 1407.00
		// 2145013.71

		val usd = Money.usd(1524.53)
		UserOrder(
			fromMoney = usd,
			toMoney = usd.exchangeKrw(exchangeRateValue),
		).let { userOrderRepository.save(it) }

	}

	@Transactional(readOnly = true)
	fun findUserOrder(id: Long): UserOrder {
		return userOrderRepository.findById(id).orElseThrow()
	}
}
