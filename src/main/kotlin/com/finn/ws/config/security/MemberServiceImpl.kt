package com.finn.ws.config.security

import com.finn.ws.config.security.data.Member
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
        private val memberRepository: MemberRepository,
        private val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    fun saveMember(member: Member): Member {
        member.password = passwordEncoder.encode(member.password)
        return memberRepository.save(member)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return memberRepository.findByName(username!!)?.getAuthorities()
                ?: throw UsernameNotFoundException("Username [$username] not found.")
    }

}