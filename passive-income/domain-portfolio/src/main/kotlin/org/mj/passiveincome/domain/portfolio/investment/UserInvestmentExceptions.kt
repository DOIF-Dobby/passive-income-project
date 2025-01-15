package org.mj.passiveincome.domain.portfolio.investment

import org.mj.passiveincome.system.core.base.BaseException

/**
 * 이미 투자에 추가된 주식인 경우 중복으로 추가할 수 없다.
 */
class DuplicateStockInInvestmentException : BaseException(messageProperty = "user-investment.duplicate-stock-in-investment")
