package org.mj.passiveincome.system.web.response

import java.io.Serializable

interface Content<T> {

  fun getContent(): List<T>

  companion object {
    fun <T> of(content: List<T>): Content<T> = object : Content<T>, Serializable {
      override fun getContent(): List<T> {
        return content
      }
    }

    fun empty(): Content<Unit> = of(emptyList())
  }

}

