package org.mj.passiveincome.system.web.exception

import io.kotest.core.spec.style.DescribeSpec
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

class ControllerExceptionHandlerTest : DescribeSpec({
  describe("Controller Exception Handler Test") {
    context("Exception이 발생하지 않은 경우") {

      val request = post("/person").locale(Locale.KOREAN)
        .content("""{"name":"MJ", "age":30}""")
        .contentType("application/json")

      it("200 응답") {
        runTestWithContext { mockMvc ->
          mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().json("""{"code":"00","message":"${messageAccessor.getMessage("base.ok")}"}"""))
        }
      }
    }

    context("non-null property에 null로 요청한 경우") {
      val request = post("/person").locale(Locale.KOREAN)
        .content("""{"name":null, "age":30}""")
        .contentType("application/json")

      it("400 응답") {
        runTestWithContext { mockMvc ->
          mockMvc.perform(request)
            .andExpect(status().isBadRequest)
            .andExpect(content().json("""{"code":"99","message":"${messageAccessor.getMessage("web.error.invalid-request-data")}"}"""))
        }
      }
    }

    context("Exception이 발생한 경우") {
      val request = post("/throw").locale(Locale.KOREAN)

      it("500 응답") {
        runTestWithContext { mockMvc ->
          mockMvc.perform(request)
            .andExpect(status().isInternalServerError)
            .andExpect(content().json("""{"code":"99","message":"${messageAccessor.getMessage("base.fail")}"}"""))
        }
      }
    }

    context("ApiException이 발생한 경우") {
      val request = post("/throw-api-ex").locale(Locale.KOREAN)

      it("ApiException의 HttpStatus와 message로 응답") {
        runTestWithContext { mockMvc ->
          mockMvc.perform(request)
            .andExpect(status().isNotFound)
            .andExpect(content().json("""{"code":"99","message":"실패 테스트"}"""))
        }
      }
    }
  }
}) {

  companion object {
    private val contextRunner = WebApplicationContextRunner()
      .withConfiguration(
        AutoConfigurations.of(
          WebMvcAutoConfiguration::class.java,
          MockMvcAutoConfiguration::class.java,
        )
      )
      .withUserConfiguration(ControllerExceptionHandler::class.java)
      .withUserConfiguration(TestController::class.java)

    private fun runTestWithContext(assertions: (MockMvc) -> Unit) {
      contextRunner.run { context ->
        val mockMvc = context.getBean(MockMvc::class.java)
        assertions(mockMvc)
      }
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
