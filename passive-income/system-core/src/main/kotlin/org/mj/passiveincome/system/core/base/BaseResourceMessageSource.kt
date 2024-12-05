package org.mj.passiveincome.system.core.base

import org.mj.passiveincome.system.core.base.BaseResourceMessageSource.Companion.getAccessor
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

/**
 * 시스템 메세지 소스
 */
class BaseResourceMessageSource : ResourceBundleMessageSource() {
  init {
    val messageBundle = getMessageBundle()
    setBasenames(*messageBundle)
  }

  companion object {
    fun getAccessor(): MessageSourceAccessor {
      return MessageSourceAccessor(BaseResourceMessageSource())
    }

    /**
     * 클래스패스 하위 messages 디렉토리에 있는 모든 properties 파일을 가져와서 basename으로 변환 후 반환
     */
    fun getMessageBundle(): Array<String> {
      val resolver = PathMatchingResourcePatternResolver()
      val resources = resolver.getResources("classpath*:messages/*.properties")

      return resources.map { resource ->
        val nameWithoutExtension = resource.file.nameWithoutExtension
        val basename = nameWithoutExtension.substring(0, nameWithoutExtension.lastIndexOf("_"))
        "messages/$basename"
      }.toSet().toTypedArray()
    }
  }
}

val messageAccessor = getAccessor()

fun getBundleMessage(code: String, args: Array<Any>? = null, defaultMessage: String = ""): String {
  return messageAccessor.getMessage(code, args, defaultMessage)
}
