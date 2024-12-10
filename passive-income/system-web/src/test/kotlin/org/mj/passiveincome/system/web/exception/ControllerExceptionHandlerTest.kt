package org.mj.passiveincome.system.web.exception

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.core.base.messageAccessor
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration
import org.springframework.boot.test.context.runner.WebApplicationContextRunner
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.Locale

class ControllerExceptionHandlerTest {

  private val contextRunner = WebApplicationContextRunner()
    .withConfiguration(
      AutoConfigurations.of(
        WebMvcAutoConfiguration::class.java,
        MockMvcAutoConfiguration::class.java,
      )
    )
    .withUserConfiguration(ControllerExceptionHandler::class.java)
    .withUserConfiguration(TestController::class.java)

  @Test
  @DisplayName("정상 테스트")
  fun okTest() {
    contextRunner.run { context ->
      val mockMvc = context.getBean(MockMvc::class.java)

      mockMvc.perform(
        post("/person").locale(Locale.KOREAN)
          .content("""{"name":"MJ", "age":30}""")
          .contentType("application/json")
      )
        .andExpect(status().isOk)
        .andExpect(content().json("""{"code":"00","message":"${messageAccessor.getMessage("base.ok")}"}"""))
    }
  }

  @Test
  @DisplayName("non-null property null 요청")
  fun nonNullPropertySetNullTest() {
    contextRunner.run { context ->
      val mockMvc = context.getBean(MockMvc::class.java)

      mockMvc.perform(
        post("/person").locale(Locale.KOREAN)
          .content("""{"name":null, "age":30}""")
          .contentType("application/json")
      )
        .andExpect(status().isBadRequest)
        .andExpect(content().json("""{"code":"99","message":"${messageAccessor.getMessage("web.error.invalid-request-data")}"}"""))
    }
  }

  @Test
  @DisplayName("throw exception")
  fun throwExTest() {
    contextRunner.run { context ->
      val mockMvc = context.getBean(MockMvc::class.java)

      mockMvc.perform(
        post("/throw").locale(Locale.KOREAN)
      )
        .andExpect(status().isInternalServerError)
        .andExpect(content().json("""{"code":"99","message":"${messageAccessor.getMessage("base.fail")}"}"""))
    }
  }

  @Test
  @DisplayName("throw api exception")
  fun throwApiExTest() {
    contextRunner.run { context ->
      val mockMvc = context.getBean(MockMvc::class.java)

      mockMvc.perform(
        post("/throw-api-ex").locale(Locale.KOREAN)
      )
        .andExpect(status().isNotFound)
        .andExpect(content().json("""{"code":"99","message":"실패 테스트"}"""))
    }
  }
}

@RestController
class TestController {

  @PostMapping("/person")
  fun createPerson(@RequestBody person: CreatePerson): BaseResponse {
    return BaseResponse.ok()
  }

  @PostMapping("/throw")
  fun throwEx() {
    throw RuntimeException()
  }

  @PostMapping("/throw-api-ex")
  fun throwApiEx() {
    throw TestApiException()
  }
}

data class CreatePerson(
  val name: String,
  val age: Int,
)

class TestApiException : ApiException(httpStatus = HttpStatus.NOT_FOUND, messageProperty = "test.fail")
