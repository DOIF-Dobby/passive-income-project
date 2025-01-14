import org.mj.passiveincome.domain.user.User
import org.mj.passiveincome.domain.user.UserFixtures
import org.mj.passiveincome.domain.user.UserRepository
import org.mj.passiveincome.system.security.oauth2.user.DummyAuthenticationUtil

class DummyAuthUtil {
  companion object {
    fun dobby(userRepository: UserRepository): User {
      val user = userRepository.save(UserFixtures.dobby())
      DummyAuthenticationUtil.setDummyAuthentication(user.id)

      return user
    }
  }
}
