package org.mj.passiveincome.domain.portfolio.interest.service

import org.mj.passiveincome.system.core.base.BaseException

/**
 * 이미 그룹에 추가된 주식인 경우 중복으로 추가할 수 없다.
 */
class DuplicateStockInGroupException : BaseException(messageProperty = "interest-stock.duplicate-in-group")
