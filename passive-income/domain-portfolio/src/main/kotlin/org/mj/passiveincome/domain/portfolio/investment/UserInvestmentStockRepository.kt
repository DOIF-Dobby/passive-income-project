package org.mj.passiveincome.domain.portfolio.investment

import org.springframework.data.jpa.repository.JpaRepository

interface UserInvestmentStockRepository : JpaRepository<UserInvestmentStock, Long> {
}
