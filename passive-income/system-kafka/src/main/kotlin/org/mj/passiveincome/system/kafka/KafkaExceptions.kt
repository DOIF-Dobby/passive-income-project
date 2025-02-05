package org.mj.passiveincome.system.kafka

import org.mj.passiveincome.system.core.base.BaseException

class KafkaSerializationException : BaseException(message = "Kafka serialization error")

class KafkaDeserializationException : BaseException(message = "Kafka deserialization error")
