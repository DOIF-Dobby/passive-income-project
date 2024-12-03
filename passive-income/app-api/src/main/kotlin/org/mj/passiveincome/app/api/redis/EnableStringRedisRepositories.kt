package org.mj.passiveincome.app.api.redis

import org.springframework.context.annotation.Import
import org.springframework.data.redis.repository.configuration.RedisRepositoriesRegistrar
import org.springframework.data.repository.config.DefaultRepositoryBaseClass
import kotlin.reflect.KClass

@Import(RedisRepositoriesRegistrar::class)
annotation class EnableStringRedisRepositories(
  val repositoryFactoryBeanClass: KClass<*> = StringRedisRepositoryFactoryBean::class,
  val repositoryBaseClass: KClass<*> = DefaultRepositoryBaseClass::class
)
