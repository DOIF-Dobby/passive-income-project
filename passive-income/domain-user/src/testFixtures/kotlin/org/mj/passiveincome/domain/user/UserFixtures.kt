package org.mj.passiveincome.domain.user

class UserFixtures {

  companion object {
    fun user1(): User {
      return User(
        username = "Test User1",
      )
    }

    fun user2(): User {
      return User(
        username = "Test User2",
      )
    }
  }
}
