import org.mj.passiveincome.system.core.event.EventPublisher
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class EventPublisherTestConfig {

  @Bean
  fun eventPublisher(): EventPublisher {
    return EventPublisher()
  }
}
