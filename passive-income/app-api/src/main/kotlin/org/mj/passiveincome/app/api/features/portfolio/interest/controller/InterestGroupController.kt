package org.mj.passiveincome.app.api.features.portfolio.interest.controller

import jakarta.validation.Valid
import org.mj.passiveincome.app.api.features.portfolio.interest.service.CreateInterestGroup
import org.mj.passiveincome.app.api.features.portfolio.interest.service.InterestGroupResponse
import org.mj.passiveincome.app.api.features.portfolio.interest.service.InterestGroupService
import org.mj.passiveincome.system.core.base.BaseResponse
import org.mj.passiveincome.system.core.base.BaseResponseDetail
import org.mj.passiveincome.system.web.response.BaseResponseContent
import org.mj.passiveincome.system.web.response.content
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class InterestGroupController(
  private val interestGroupService: InterestGroupService,
) {

  /**
   * 관심 그룹 생성
   */
  @PostMapping("/interest-groups")
  fun createInterestGroup(@RequestBody @Valid payload: CreateInterestGroup): BaseResponse {
    interestGroupService.createInterestGroup(payload)
    return BaseResponse.ok()
  }

  /**
   * 사용자 관심 그룹 목록
   */
  @GetMapping("/interest-groups")
  fun findUserInterestGroups(@RequestParam userId: Long): BaseResponseContent<InterestGroupResponse> {
    val groups = interestGroupService.findInterestGroups(userId)
    return BaseResponseDetail.content(groups)
  }
}
