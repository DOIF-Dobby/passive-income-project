package org.mj.passiveincome.system.data

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun notFound(): Nothing {
  throw EntityNotFoundException()
}

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID, throwBlock: () -> Nothing = notFound()): T {
  return this.findByIdOrNull(id) ?: throwBlock()
}

fun <T, ID, R> CrudRepository<T, ID>.findAllWithMap(transform: (T) -> R): List<R> {
  return this.findAll()
    .map { transform(it) }
}
