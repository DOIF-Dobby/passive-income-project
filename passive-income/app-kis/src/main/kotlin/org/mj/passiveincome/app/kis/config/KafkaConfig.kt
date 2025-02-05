package org.mj.passiveincome.app.kis.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.mj.passiveincome.system.kafka.KafkaCommand
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer

@Configuration
class KafkaConfig {

  @Bean
  fun producerFactory(): ProducerFactory<String, String> {
    val config = mapOf<String, Any>(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
    )

    return DefaultKafkaProducerFactory(config)
  }

  @Bean
  fun kafkaTemplate(): KafkaTemplate<String, String> {
    return KafkaTemplate(producerFactory())
  }

  @Bean
  fun kafkaCommand(): KafkaCommand {
    return KafkaCommand(kafkaTemplate())
  }

  @Bean
  fun consumerFactory(): ConsumerFactory<String, Any> {
    val config = mapOf<String, Any>(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
      ConsumerConfig.GROUP_ID_CONFIG to "group_1",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
    )

    return DefaultKafkaConsumerFactory(config)
  }

  @Bean
  fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Any>> {
    val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
    factory.consumerFactory = consumerFactory()
    return factory
  }
}
