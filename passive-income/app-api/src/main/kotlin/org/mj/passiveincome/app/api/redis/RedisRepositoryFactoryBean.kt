package org.mj.passiveincome.app.api.redis

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import org.springframework.data.repository.core.EntityInformation
import org.springframework.data.repository.core.RepositoryInformation
import org.springframework.data.repository.core.RepositoryMetadata
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport
import org.springframework.data.repository.core.support.RepositoryFactorySupport
import kotlin.reflect.KClass

@NoRepositoryBean
interface StringRedisRepository<T, ID> : Repository<T, ID> {

}

class SimpleStringRedisRepository<T, ID>(
) : StringRedisRepository<T, ID> {

}

class StringRedisRepositoryFactoryBean<T : StringRedisRepository<S, ID>, S, ID>(
  repositoryInterface: KClass<T>
) : RepositoryFactoryBeanSupport<T, S, ID>(repositoryInterface.java) {

  override fun createRepositoryFactory(): RepositoryFactorySupport {
    return StringRedisRepositoryFactory()
  }

  override fun afterPropertiesSet() {
    super.afterPropertiesSet() // 부모 클래스의 초기화 호출
  }
}

class StringRedisRepositoryFactory : RepositoryFactorySupport() {
  override fun <T : Any?, ID : Any?> getEntityInformation(domainClass: Class<T>): EntityInformation<T, ID> {
    throw UnsupportedOperationException()
  }

  override fun getTargetRepository(metadata: RepositoryInformation): Any {
    return SimpleStringRedisRepository<Any, Any>()
  }

  override fun getRepositoryBaseClass(metadata: RepositoryMetadata): Class<*> {
    return SimpleStringRedisRepository::class.java
  }
}


