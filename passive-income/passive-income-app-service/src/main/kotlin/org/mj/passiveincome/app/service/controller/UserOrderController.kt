package org.mj.passiveincome.app.service.controller

import org.mj.passiveincome.app.service.dto.UserOrderDto
import org.mj.passiveincome.app.service.service.UserOrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserOrderController(
  private val userOrderService: UserOrderService
) {

  @PostMapping("/order")
  fun createOrder() {
    userOrderService.createOrder()
  }

  @GetMapping("/order/{id}")
  fun findUserOrder(@PathVariable id: Long): UserOrderDto {
    val userOrder = userOrderService.findUserOrder(id)
    return UserOrderDto.from(userOrder)
  }
}