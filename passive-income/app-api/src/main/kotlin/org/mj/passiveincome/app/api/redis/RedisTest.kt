package org.mj.passiveincome.app.api.redis

import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.mj.passiveincome.system.data.redis.RedisService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
class RedisController(
  private val redisTest: RedisTest,
) {

  @PostMapping("/redis/test")
  fun save() {
    redisTest.save()
  }

  @GetMapping("/redis/test")
  fun find(): BaseResponseDetail<TestEntity> {
    return BaseResponseDetail.ok(redisTest.find())
  }
}

@Service
class RedisTest(
  private val redisService: RedisService,
  private val testEntityRepository: TestEntityRepository,
) {

  @Transactional
  fun save() {
    val entity = TestEntity(
      id = "1",
      value = "value",
    )

    redisService.save("redis-test:1", entity, duration = Duration.ofSeconds(10))
  }

  fun find(): TestEntity {
//    return testEntityRepository.findById("redis-test:1").orElseThrow { RuntimeException("Not found") }
//    val entity = redisService.findById("redis-test:1", RedisEntity::class)
//    println(entity)
//    return entity ?: throw RuntimeException("Not found")
    throw RuntimeException("Not found")
  }
}

data class TestEntity(
  val id: String,
  val value: String,
)

interface TestEntityRepository : StringRedisRepository<TestEntity, String>
