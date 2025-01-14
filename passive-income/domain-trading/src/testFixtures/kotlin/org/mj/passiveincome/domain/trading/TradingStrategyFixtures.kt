package org.mj.passiveincome.domain.trading

import org.mj.passiveincome.domain.user.User

class TradingStrategyFixtures {

  companion object {
    fun tradingStrategy(name: String = "test strategy 1", owner: User) = TradingStrategy(
      name = name,
      owner = owner
    )
  }
}
