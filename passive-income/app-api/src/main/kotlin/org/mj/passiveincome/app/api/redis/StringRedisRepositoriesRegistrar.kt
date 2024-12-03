package org.mj.passiveincome.app.api.redis

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport
import org.springframework.data.repository.config.RepositoryConfigurationExtension

class StringRedisRepositoriesRegistrar : RepositoryBeanDefinitionRegistrarSupport() {
  override fun getAnnotation(): Class<out Annotation> {
    return EnableStringRedisRepositories::class.java
  }

  override fun getExtension(): RepositoryConfigurationExtension {
    TODO("Not yet implemented")
  }
}
