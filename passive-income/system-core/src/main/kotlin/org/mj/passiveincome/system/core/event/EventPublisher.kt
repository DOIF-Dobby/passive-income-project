package org.mj.passiveincome.system.core.event

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

@Component
class EventPublisher : ApplicationEventPublisherAware {
  override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
    publisher = applicationEventPublisher
  }

  companion object {
    private lateinit var publisher: ApplicationEventPublisher

    fun publishEvent(event: Any) {
      publisher.publishEvent(event)
    }
  }
}
