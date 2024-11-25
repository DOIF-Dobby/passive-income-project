package org.mj.passiveincome.app.service.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserOrderRepository : JpaRepository<UserOrder, Long> {
}