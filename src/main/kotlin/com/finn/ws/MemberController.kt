package com.finn.ws

import com.finn.ws.config.security.MemberServiceImpl
import com.finn.ws.config.security.data.Member
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("sign_up")
class MemberController(private val memberService: MemberServiceImpl) {

    @PostMapping
    fun signUp(@RequestBody signUpRequest: SignUpRequest): Member = memberService.saveMember(Member(signUpRequest.username, signUpRequest.password))
}

data class SignUpRequest(
        val username: String,
        val password: String
)